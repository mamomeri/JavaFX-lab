package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Controller;

import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.PrintJob;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.PrintJobStatus;
import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Model.SpoolerModel;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Vista secundaria: detalle + estadÃ­sticas + historial.
 *
 * RESPONSABILIDAD (arquitectura):
 * - PresentaciÃ³n y bindings.
 * - Cero lÃ³gica de negocio (eso vive en el modelo).
 * - Cero navegaciÃ³n directa (usa callback onBack).
 */
public class SecondaryController {

    /* =======================
       ========== UI =========
       ======================= */

    @FXML private Button btnBack;

    @FXML private Label activeDetailLbl;
    @FXML private ProgressBar activeProgressBar2;

    @FXML private Label completedLbl;
    @FXML private Label canceledLbl;
    @FXML private Label avgMsPerPageLbl;

    @FXML private TableView<PrintJob> historyTable;
    @FXML private TableColumn<PrintJob, String> hTitle;
    @FXML private TableColumn<PrintJob, Number> hPages;
    @FXML private TableColumn<PrintJob, Number> hPriority;

    // IMPORTANTE:
    // No uses Object aquÃ­. El tipo real del dominio es PrintJobStatus (enum).
    @FXML private TableColumn<PrintJob, PrintJobStatus> hStatus;

    /* =======================
       ===== Dependencias =====
       ======================= */

    // Modelo compartido (inyectado por el entry point)
    private SpoolerModel model;

    // Callback para volver (inyectado por el entry point)
    private Runnable onBack = () -> {};

    /* =======================
       ===== InyecciÃ³n =======
       ======================= */

    public void setModel(SpoolerModel model) {
        this.model = model;
        bindModelToUI();
        refreshStats(); // al entrar, mostrar stats actuales
    }

    public void setOnBack(Runnable onBack) {
        this.onBack = (onBack != null) ? onBack : () -> {};
    }

    /* =======================
       ===== Lifecycle =======
       ======================= */

    @FXML
    private void initialize() {
        // Configura columnas (esto no depende del modelo, solo de PrintJob)
        hTitle.setCellValueFactory(d -> d.getValue().titleProperty());
        hPages.setCellValueFactory(d -> d.getValue().pagesProperty());
        hPriority.setCellValueFactory(d -> d.getValue().priorityProperty());

        // AquÃ­ estÃ¡ la correcciÃ³n del error:
        // La columna debe ser <PrintJob, PrintJobStatus>
        hStatus.setCellValueFactory(d -> d.getValue().statusProperty());

        // BotÃ³n volver: desacoplado del Navigator (arquitectura limpia)
        btnBack.setOnAction(e -> onBack.run());
    }

    /* =======================
       ===== Binding UI ======
       ======================= */

    private void bindModelToUI() {
        if (model == null) return;

        // 1) Historial: la tabla observa la lista del modelo
        historyTable.setItems(model.getHistory());

        // 2) Si cambia el historial, recalculamos stats
        model.getHistory().addListener((ListChangeListener<PrintJob>) c -> refreshStats());

        // 3) Texto de detalle del trabajo activo
        activeDetailLbl.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            PrintJob job = model.activeJobProperty().get();
                            if (job == null) return "(ninguno)";
                            return "Imprimiendo: " + job.getTitle()
                                    + " (" + job.getProgressPages()
                                    + "/" + job.getPages() + ")";
                        },
                        model.activeJobProperty()
                )
        );

        // 4) Barra de progreso del activo: hay que re-bind cuando cambia el job activo
        model.activeJobProperty().addListener((obs, oldJob, newJob) -> bindProgressToActive(newJob));

        // Bind inicial
        bindProgressToActive(model.activeJobProperty().get());
    }

    private void bindProgressToActive(PrintJob job) {
        activeProgressBar2.progressProperty().unbind();

        if (job == null) {
            activeProgressBar2.setProgress(0);
            return;
        }

        // Progreso = progressPages / pages (ratio)
        activeProgressBar2.progressProperty().bind(
                Bindings.createDoubleBinding(
                        job::getProgressRatio,
                        job.progressPagesProperty(),
                        job.pagesProperty()
                )
        );
    }

    /* =======================
       ===== PresentaciÃ³n =====
       ======================= */

    /**
     * Recalcula estadÃ­sticas (presentaciÃ³n).
     * Esto NO cambia el modelo; solo lee y muestra.
     */
    public void refreshStats() {
        if (model == null) return;

        completedLbl.setText(String.valueOf(model.getCompletedCount()));
        canceledLbl.setText(String.valueOf(model.getCanceledCount()));
        avgMsPerPageLbl.setText(String.format("%.2f", model.getAverageMsPerPage()));
    }
}

