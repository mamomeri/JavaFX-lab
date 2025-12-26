package com.HabilidadesProfesionales.Examples.CSSstyling.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.util.Objects;


public class CSSstylingView {
    @FXML
    Label titleLabel;
    @FXML
    Label textLabel;

    public Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(
                            getClass().getResource("/Examples/CSSstyling/View/CSSstylingView.fxml")
                    )
            );

            Parent root = loader.load();

            // Hook: cuando el root ya estÃ© dentro de una Scene, aplica el CSS
            root.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    String css = Objects.requireNonNull(
                            getClass().getResource("/Examples/CSSstyling/View/CSSstyling.css")
                    ).toExternalForm();

                    if (!newScene.getStylesheets().contains(css)) {
                        newScene.getStylesheets().add(css);
                    }
                }
            });

            return root;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

