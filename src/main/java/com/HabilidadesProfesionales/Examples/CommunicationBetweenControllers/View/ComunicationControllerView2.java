package com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ComunicationControllerView2 {
    public Parent load(){
        try {
            FXMLLoader loader = FXMLLoader.load(getClass().getResource("/Examples/CommunicationBetweenControllers/View/Scene 2.fxml"));
            Parent root = loader.load();
            return root;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

