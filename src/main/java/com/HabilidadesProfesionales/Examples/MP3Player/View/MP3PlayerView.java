package com.HabilidadesProfesionales.Examples.MP3Player.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MP3PlayerView {
    public Parent load() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Examples/MP3Player/View/MP3PlayerScene.fxml"));
            Parent root = loader.load();
            return  root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
