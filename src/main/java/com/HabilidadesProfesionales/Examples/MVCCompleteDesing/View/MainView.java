package com.HabilidadesProfesionales.Examples.MVCCompleteDesing.View;

import com.HabilidadesProfesionales.Examples.MVCCompleteDesing.Controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * View responsable de cargar MainView.fxml
 * y exponer su Controller para inyecciÃ³n.
 */
public class MainView {

    private FXMLLoader loader;

    public Parent load() {
        try {
            loader = new FXMLLoader(
                    getClass().getResource(
                            "/Examples/MVCCompleteDesing/View/MainView.fxml"
                    )
            );
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando MainView.fxml", e);
        }
    }

    public MainController getController() {
        return loader.getController();
    }
}

