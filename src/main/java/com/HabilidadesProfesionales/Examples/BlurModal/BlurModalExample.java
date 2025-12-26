package com.HabilidadesProfesionales.Examples.BlurModal;

import com.HabilidadesProfesionales.App.IRawExample;
import com.HabilidadesProfesionales.Examples.BlurModal.Controller.ConfirmOverlayController;
import com.HabilidadesProfesionales.Examples.BlurModal.Model.ConfirmOverlayModel;
import com.HabilidadesProfesionales.Examples.BlurModal.View.ConfirmOverlayView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BlurModalExample implements IRawExample {

    @Override
    public String name() {
        return "ConfirmOverlay (MVC, reusable)";
    }

    @Override
    public Parent view() {

        // ===== Contenido normal =====
        Rectangle square = new Rectangle(110, 110, Color.DODGERBLUE);
        Circle circle = new Circle(55, Color.ORANGE);
        Label label = new Label("Texto X");
        Button open = new Button("Abrir confirmaciÃ³n");

        VBox content = new VBox(14, square, circle, label, open);
        content.setAlignment(Pos.CENTER);

        StackPane backgroundLayer = new StackPane(content);
        backgroundLayer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backgroundLayer.setStyle("-fx-background-color: #f7f7f7;");

        StackPane root = new StackPane(backgroundLayer);

        // ===== Overlay MVC =====
        ConfirmOverlayModel model = new ConfirmOverlayModel();
        ConfirmOverlayView overlayView = new ConfirmOverlayView(model);
        ConfirmOverlayController overlay = new ConfirmOverlayController(root, backgroundLayer, model, overlayView);

        // âœ… SONIDITO (el tuyo)
        overlay.setClickSound("/Sounds/tick.mp3");

        open.setOnAction(e -> {
            // (si quieres que el click suene tambiÃ©n al abrir, puedes llamarlo antes de show,
            //  pero por defecto suena en aceptar/cancelar/click afuera/ESC)
            overlay.show(
                    "Aplicar cambios",
                    "Â¿EstÃ¡s seguro?",
                    () -> System.out.println("ACEPTADO âœ…"),
                    () -> System.out.println("CANCELADO âŒ")
            );
        });

        return root;
    }
}

