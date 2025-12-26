package com.HabilidadesProfesionales.Examples.KeyEvent.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class KeyEventView {

    public Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Examples/KeyEventExample/View/KeyEventView.fxml")
            );
            Parent root = loader.load();
            return root;
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar DatePickerScene.fxml", e);
        }
    }
}
