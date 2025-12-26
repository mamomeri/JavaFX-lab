package com.HabilidadesProfesionales.Examples.MediaView.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MediaViewView {
    public Parent load() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Examples/MediaView/View/MediaViewScene.fxml"));
            Parent root = loader.load();
            return root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
