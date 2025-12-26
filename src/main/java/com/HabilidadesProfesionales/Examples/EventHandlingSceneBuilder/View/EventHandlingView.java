package com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class EventHandlingView {

    private static final String FXML =
            "/Examples/EventHandlingSceneBuilder/View/event_handling_view.fxml";

    public Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando EventHandlingSceneBuilder FXML", e);
        }
    }
}

