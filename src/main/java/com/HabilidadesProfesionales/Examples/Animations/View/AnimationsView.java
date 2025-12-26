package com.HabilidadesProfesionales.Examples.Animations.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class AnimationsView {
    public Parent load() {
        try {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/Examples/Animatios/View/AnimationsView.fxml"));
            Parent root = loader.load();
            return root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
