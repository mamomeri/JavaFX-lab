package com.HabilidadesProfesionales.Examples.GUIFest.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class DatePickerView {

    public Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Examples/GUIFest/View/DatePickerScene.fxml")
            );
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar DatePickerScene.fxml", e);
        }
    }
}

