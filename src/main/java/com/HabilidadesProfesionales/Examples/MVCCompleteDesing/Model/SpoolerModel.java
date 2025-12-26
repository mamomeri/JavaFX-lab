package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;

import java.time.Instant;
import java.util.Comparator;

/**
 * MODELO (Dominio) del "Spooler" (cola de impresiÃ³n).
 *
 * Responsabilidad:
 * - Mantener estado: cola, trabajo activo, historial.
 * - Ejecutar reglas del dominio: encolar, pausar, cancelar, elegir siguiente.
 * - Simular impresiÃ³n: 1 pÃ¡gina cada X ms (Timeline).
 *
 * Importante de arquitectura:
 * - No conoce Views.
 * - No conoce Controllers.
 * - No maneja Stage/Scene.
 */
public class SpoolerModel {

    // Cola observable para que TableView se actualice automÃ¡ticamente
    private final ObservableList<PrintJob> queue = FXCollections.observableArrayList();

    // Historial observable (trabajos finalizados/cancelados)
    private final ObservableList<PrintJob> history = FXCollections.observableArrayList();

    // Trabajo activo (actualmente imprimiÃ©ndose)
    private final ObjectProperty<PrintJob> activeJob = new SimpleObjectProperty<>(null);

    // Pausa del spooler
    private final BooleanProperty paused = new SimpleBooleanProperty(false);

    // ConfiguraciÃ³n: milisegundos por pÃ¡gina (la UI lo puede bindear a un slider)
    private final IntegerProperty msPerPage = new SimpleIntegerProperty(300);

    // Reloj de simulaciÃ³n (JavaFX)
    private Timeline timeline;

    public SpoolerModel() {
        configureTimeline();

        // Si cambia msPerPage, recreamos la timeline con el nuevo intervalo
        msPerPage.addListener((obs, oldV, newV) -> configureTimeline());
    }

    /**
     * Configura (o reconfigura) el reloj de simulaciÃ³n.
     * Usamos javafx.util.Duration (NO java.time.Duration) porque Timeline la requiere.
     */
    private void configureTimeline() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(
                new KeyFrame(Duration.millis(msPerPage.get()), e -> tick())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Si ya estaba corriendo antes, vuelve a correr
        timeline.play();
    }

    /**
     * Un "tick" de la simulaciÃ³n.
     * - Si estÃ¡ pausado: no avanza.
     * - Si no hay activeJob: toma el siguiente.
     * - Si hay activeJob: incrementa progreso.
     */
    private void tick() {
        if (paused.get()) return;

        // Si no hay trabajo activo, intentamos seleccionar el siguiente de la cola
        if (activeJob.get() == null) {
            pickNextJobIfAny();
            return;
        }

        PrintJob job = activeJob.get();

        // Si por alguna razÃ³n ya estÃ¡ finalizado, limpiamos y seguimos
        if (job.isFinished()) {
            activeJob.set(null);
            pickNextJobIfAny();
            return;
        }

        // Avanzar una pÃ¡gina
        int newProgress = job.progressPagesProperty().get() + 1;
        job.progressPagesProperty().set(newProgress);

        // Si terminÃ³, pasamos a historial y liberamos activeJob
        if (newProgress >= job.pagesProperty().get()) {
            job.setStatus(PrintJobStatus.DONE);
            job.markFinished();
            history.add(job);

            activeJob.set(null);
            pickNextJobIfAny();
        }
    }

    /**
     * Selecciona el siguiente trabajo si existe.
     *
     * Regla del dominio:
     * - Elige el job con mayor prioridad.
     * - Si hay empate, el stream max se queda con uno (puedes mejorar a FIFO si quieres).
     */
    private void pickNextJobIfAny() {
        if (queue.isEmpty()) return;

        PrintJob next = queue.stream()
                .max(Comparator.comparingInt(PrintJob::getPriority))
                .orElse(null);

        if (next == null) return;

        queue.remove(next);
        activeJob.set(next);

        next.setStatus(PrintJobStatus.PRINTING);
        next.markStarted();
    }

    /* ======================================================
       =================== API DEL DOMINIO ==================
       ====================================================== */

    /** Arranca la simulaciÃ³n (si ya estÃ¡ creada). */
    public void start() {
        if (timeline != null) timeline.play();
    }

    /** Detiene la simulaciÃ³n. Ãštil si cierras la app. */
    public void stop() {
        if (timeline != null) timeline.stop();
    }

    /** Encola un trabajo nuevo. */
    public void enqueueJob(String title, int pages, int priority) {
        PrintJob job = new PrintJob(title, pages, priority);
        job.setStatus(PrintJobStatus.QUEUED);
        queue.add(job);
    }

    /** Pausa o reanuda. */
    public void togglePause() {
        paused.set(!paused.get());

        // Reflejar el estado en el job activo (si existe)
        PrintJob job = activeJob.get();
        if (job != null && !job.isFinished()) {
            job.setStatus(paused.get() ? PrintJobStatus.PAUSED : PrintJobStatus.PRINTING);
        }
    }

    /** Cancela el job activo. */
    public void cancelActiveJob() {
        PrintJob job = activeJob.get();
        if (job == null) return;

        job.setStatus(PrintJobStatus.CANCELED);
        job.markFinished();
        history.add(job);

        activeJob.set(null);
        pickNextJobIfAny();
    }

    /** Cancela un job que estÃ¡ en cola. */
    public void cancelQueuedJob(PrintJob job) {
        if (job == null) return;

        if (queue.remove(job)) {
            job.setStatus(PrintJobStatus.CANCELED);
            job.markFinished();
            history.add(job);
        }
    }

    /* ======================================================
       ===================== ESTADÃSTICAS ====================
       ====================================================== */

    public int getCompletedCount() {
        return (int) history.stream().filter(j -> j.getStatus() == PrintJobStatus.DONE).count();
    }

    public int getCanceledCount() {
        return (int) history.stream().filter(j -> j.getStatus() == PrintJobStatus.CANCELED).count();
    }

    /**
     * Promedio de milisegundos por pÃ¡gina (usando jobs DONE).
     *
     * Nota tÃ©cnica:
     * - AquÃ­ usamos java.time.Duration pero con nombre completo para evitar choque con javafx.util.Duration.
     */
    public double getAverageMsPerPage() {
        var doneJobs = history.stream()
                .filter(j -> j.getStatus() == PrintJobStatus.DONE)
                .toList();

        if (doneJobs.isEmpty()) return 0;

        long totalMs = 0;
        long totalPages = 0;

        for (PrintJob j : doneJobs) {
            Instant s = j.startedAtProperty().get();
            Instant f = j.finishedAtProperty().get();
            if (s == null || f == null) continue;

            long ms = java.time.Duration.between(s, f).toMillis();
            totalMs += ms;
            totalPages += j.getPages();
        }

        if (totalPages == 0) return 0;
        return totalMs / (double) totalPages;
    }

    /* ======================================================
       ================== EXPOSICIÃ“N (UI) ====================
       ====================================================== */

    public ObservableList<PrintJob> getQueue() { return queue; }
    public ObservableList<PrintJob> getHistory() { return history; }

    public ObjectProperty<PrintJob> activeJobProperty() { return activeJob; }
    public BooleanProperty pausedProperty() { return paused; }
    public IntegerProperty msPerPageProperty() { return msPerPage; }
}

