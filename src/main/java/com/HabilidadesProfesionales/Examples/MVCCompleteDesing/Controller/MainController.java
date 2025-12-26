package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Controller;

import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.PrintJob;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.PrintJobStatus;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.SpoolerModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * CONTROLLER principal (pantalla MainView).
 *
 * Responsabilidad:
 * - Manejar eventos de UI (botones, selecciÃ³n).
 * - Llamar al modelo (SpoolerModel).
 * - Hacer bindings para que la UI se actualice sola.
 *
 * NO debe:
 * - Crear Stage/Scene (eso es del lab/Navigator).
 * - Definir reglas del dominio (eso es del modelo).
 */
public class MainController {

    /* =======================
       ========== UI =========
       ======================= */

    @FXML private TableView<PrintJob> queueTable;

    @FXML private TableColumn<PrintJob, String> colTitle;
    @FXML private TableColumn<PrintJob, Number> colPages;
    @FXML private TableColumn<PrintJob, Number> colPriority;

    // âœ… IMPORTANTE: NO Object. El tipo real del dominio es PrintJobStatus.
    @FXML private TableColumn<PrintJob, PrintJobStatus> colStatus;

    @FXML private TableColumn<PrintJob, String> colProgress;

    @FXML private Slider speedSlider;

    @FXML private Label activeJobLbl;
    @FXML private Label pausedLbl;
    @FXML private ProgressBar activeProgressBar;

    @FXML private Button btnNewJob;
    @FXML private Button btnPause;
    @FXML private Button btnCancelActive;
    @FXML private Button btnCancelSelected;
    @FXML private Button btnGoStats;

    @FXML private Label statusLbl;

    /* =======================
       ===== Dependencias =====
       ======================= */

    private SpoolerModel model;

    /**
     * Callback de navegaciÃ³n hacia la pantalla secundaria.
     * Se inyecta desde el Example (entry point).
     */
    private Runnable onGoStats = () -> {};

    // Listener para re-bindear la ProgressBar cuando cambia el job activo
    private final ChangeListener<PrintJob> activeJobListener = (obs, oldJob, newJob) -> bindActiveProgressTo(newJob);

    /* =======================
       ===== InyecciÃ³n =======
       ======================= */

    public void setModel(SpoolerModel model) {
        this.model = model;
        bindModelToUI();
    }

    public void setOnGoStats(Runnable onGoStats) {
        this.onGoStats = (onGoStats != null) ? onGoStats : () -> {};
    }

    /* =======================
       ===== Lifecycle =======
       ======================= */

    @FXML
    private void initialize() {
        configureColumns();
        wireEvents();
    }

    /**
     * Configura columnas (solo depende de la entidad PrintJob).
     */
    private void configureColumns() {
        colTitle.setCellValueFactory(d -> d.getValue().titleProperty());
        colPages.setCellValueFactory(d -> d.getValue().pagesProperty());
        colPriority.setCellValueFactory(d -> d.getValue().priorityProperty());

        // âœ… AquÃ­ desaparece tu error: el tipo coincide (PrintJobStatus).
        colStatus.setCellValueFactory(d -> d.getValue().statusProperty());

        // "x / total" (presentaciÃ³n)
        colProgress.setCellValueFactory(d ->
                Bindings.createStringBinding(
                        () -> d.getValue().getProgressPages() + " / " + d.getValue().getPages(),
                        d.getValue().progressPagesProperty(),
                        d.getValue().pagesProperty()
                )
        );
    }

    /**
     * Conecta botones con acciones del dominio (modelo).
     */
    private void wireEvents() {
        btnNewJob.setOnAction(e -> {
            if (model == null) return;

            // Demo: luego puedes reemplazarlo por un diÃ¡logo formal
            int pages = 5 + (int) (Math.random() * 20);
            int priority = 1 + (int) (Math.random() * 5);

            model.enqueueJob("Documento " + System.currentTimeMillis(), pages, priority);
            statusLbl.setText("Encolado: pages=" + pages + ", priority=" + priority);
        });

        btnPause.setOnAction(e -> {
            if (model == null) return;
            model.togglePause();
        });

        btnCancelActive.setOnAction(e -> {
            if (model == null) return;
            model.cancelActiveJob();
        });

        btnCancelSelected.setOnAction(e -> {
            if (model == null) return;
            PrintJob selected = queueTable.getSelectionModel().getSelectedItem();
            model.cancelQueuedJob(selected);
        });

        btnGoStats.setOnAction(e -> onGoStats.run());
    }

    /* =======================
       ===== Bindings UI =====
       ======================= */

    private void bindModelToUI() {
        if (model == null) return;

        // La tabla observa la cola
        queueTable.setItems(model.getQueue());

        // Slider controla ms/pÃ¡gina (si quieres que sea int, puedes redondear, pero asÃ­ funciona)
        model.msPerPageProperty().bind(speedSlider.valueProperty());

        // Texto de pausa
        pausedLbl.textProperty().bind(
                Bindings.when(model.pausedProperty()).then("PAUSADO").otherwise("")
        );

        // Nombre del trabajo activo
        activeJobLbl.textProperty().bind(
                Bindings.createStringBinding(
                        () -> model.activeJobProperty().get() == null
                                ? "(ninguno)"
                                : model.activeJobProperty().get().getTitle(),
                        model.activeJobProperty()
                )
        );

        // Cada vez que cambie el activeJob, re-bindeamos la ProgressBar
        model.activeJobProperty().addListener(activeJobListener);

        // Bind inicial
        bindActiveProgressTo(model.activeJobProperty().get());
    }

    private void bindActiveProgressTo(PrintJob job) {
        activeProgressBar.progressProperty().unbind();

        if (job == null) {
            activeProgressBar.setProgress(0);
            return;
        }

        // ProgressBar se liga al ratio del job
        activeProgressBar.progressProperty().bind(
                Bindings.createDoubleBinding(
                        job::getProgressRatio,
                        job.progressPagesProperty(),
                        job.pagesProperty()
                )
        );
    }
}

