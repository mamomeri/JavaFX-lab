package com.HabilidadesProfesionales.Examples.ButtonExample;

import com.HabilidadesProfesionales.App.IRawExample;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ButtonManualExample  implements IRawExample {

    @Override
    public String name() {
        return "ButtonManualExample";
    }
    @Override
    public Parent view() {
        Button button = new Button("Click me");
        Label label = new Label("...");

        button.setOnAction(e -> label.setText("Hola"));

        VBox root = new VBox(10, button, label);
        root.setPadding(new Insets(20));
        return root;
    }
}

