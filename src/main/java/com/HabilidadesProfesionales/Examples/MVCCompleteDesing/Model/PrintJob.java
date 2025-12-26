package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model;

import javafx.beans.property.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad del dominio: un trabajo de impresiÃ³n.
 *
 * Nota de arquitectura:
 * - Esto NO es UI.
 * - AquÃ­ solo vive el estado del "negocio".
 * - Usamos JavaFX Properties para que la UI se actualice sola (binding).
 */
public class PrintJob {

    private final String id = UUID.randomUUID().toString();

    private final StringProperty title = new SimpleStringProperty();
    private final IntegerProperty pages = new SimpleIntegerProperty();
    private final IntegerProperty priority = new SimpleIntegerProperty(); // 1..5 por ejemplo
    private final ObjectProperty<PrintJobStatus> status = new SimpleObjectProperty<>(PrintJobStatus.QUEUED);

    private final IntegerProperty progressPages = new SimpleIntegerProperty(0);

    // Timestamps Ãºtiles para estadÃ­sticas
    private final ObjectProperty<Instant> createdAt = new SimpleObjectProperty<>(Instant.now());
    private final ObjectProperty<Instant> startedAt = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Instant> finishedAt = new SimpleObjectProperty<>(null);

    public PrintJob(String title, int pages, int priority) {
        this.title.set(title);
        this.pages.set(pages);
        this.priority.set(priority);
    }

    // ---------- Reglas pequeÃ±as de la entidad ----------
    public boolean isFinished() {
        return status.get() == PrintJobStatus.DONE
                || status.get() == PrintJobStatus.CANCELED
                || status.get() == PrintJobStatus.ERROR;
    }

    public double getProgressRatio() {
        int total = pages.get();
        if (total <= 0) return 0.0;
        return Math.min(1.0, progressPages.get() / (double) total);
    }

    // ---------- Getters + Properties (para TableView, bindings, etc.) ----------
    public String getId() { return id; }

    public StringProperty titleProperty() { return title; }
    public IntegerProperty pagesProperty() { return pages; }
    public IntegerProperty priorityProperty() { return priority; }
    public ObjectProperty<PrintJobStatus> statusProperty() { return status; }
    public IntegerProperty progressPagesProperty() { return progressPages; }

    public ObjectProperty<Instant> createdAtProperty() { return createdAt; }
    public ObjectProperty<Instant> startedAtProperty() { return startedAt; }
    public ObjectProperty<Instant> finishedAtProperty() { return finishedAt; }

    public String getTitle() { return title.get(); }
    public int getPages() { return pages.get(); }
    public int getPriority() { return priority.get(); }
    public PrintJobStatus getStatus() { return status.get(); }
    public int getProgressPages() { return progressPages.get(); }

    public void setStatus(PrintJobStatus newStatus) { status.set(newStatus); }

    public void markStarted() { startedAt.set(Instant.now()); }
    public void markFinished() { finishedAt.set(Instant.now()); }
}

