package com.HabilidadesProfesionales.Examples.BlurModal.View;

import com.HabilidadesProfesionales.Examples.BlurModal.Model.ConfirmOverlayModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConfirmOverlayView {

    private final ConfirmOverlayModel model;

    private final Rectangle dim;
    private final VBox card;

    private final Label titleLbl;
    private final Label msgLbl;

    private final Button acceptBtn;
    private final Button cancelBtn;

    public ConfirmOverlayView(ConfirmOverlayModel model) {
        this.model = model;

        // ===== Dim =====
        dim = new Rectangle();
        dim.setFill(Color.BLACK);
        dim.setOpacity(0);      // el controller lo anima
        dim.setVisible(false);
        dim.setPickOnBounds(true);

        // ===== Card =====
        titleLbl = new Label();
        titleLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        msgLbl = new Label();
        msgLbl.setStyle("-fx-font-size: 13px;");

        // Bind del texto al Model
        titleLbl.textProperty().bind(model.titleProperty());
        msgLbl.textProperty().bind(model.messageProperty());

        acceptBtn = new Button("Aceptar");
        cancelBtn = new Button("Cancelar");

        acceptBtn.setStyle("-fx-background-radius: 10; -fx-padding: 6 16;");
        cancelBtn.setStyle("-fx-background-radius: 10; -fx-padding: 6 16;");

        card = new VBox(10, titleLbl, msgLbl, acceptBtn, cancelBtn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(18));

        // CLAVE: que NO se estire a pantalla completa en StackPane
        card.setMaxSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);

        // Estilo â€œtarjetitaâ€
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 14;
                -fx-border-color: rgba(0,0,0,0.20);
                -fx-border-radius: 14;
                -fx-border-width: 1;
                """);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(18);
        shadow.setOffsetY(6);
        shadow.setColor(Color.rgb(0,0,0,0.28));
        card.setEffect(shadow);

        card.setVisible(false);
        card.setOpacity(0);
        card.setScaleX(0.96);
        card.setScaleY(0.96);
    }

    public Rectangle getDim() { return dim; }
    public VBox getCard() { return card; }
    public Button getAcceptBtn() { return acceptBtn; }
    public Button getCancelBtn() { return cancelBtn; }
}

