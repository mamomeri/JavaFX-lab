package com.HabilidadesProfesionales.Examples.ButtonExampleFXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ButtonFXMLExample {

    public Parent view() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/Examples/ButtonExampleFXML/View/button_view.fxml"
                    )
            );
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando ButtonFXMLExample", e);
        }
    }
}

