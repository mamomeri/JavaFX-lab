package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.View;

import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Controller.SecondaryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * View responsable de cargar SecondaryView.fxml
 */
public class SecondaryView {

    private FXMLLoader loader;

    public Parent load() {
        try {
            loader = new FXMLLoader(
                    getClass().getResource(
                            "/Examples/MVCCompleteDesing/View/SecondaryView.fxml"
                    )
            );
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando SecondaryView.fxml", e);
        }
    }

    public SecondaryController getController() {
        return loader.getController();
    }
}

