package com.HabilidadesProfesionales.Examples.SwitchScenes.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SwitchScenesView {

    private FXMLLoader loader;

    public Parent load(){

        try {
            loader =  new FXMLLoader(getClass().getResource("/Examples/SwitchScenes/View/Scene1.fxml"));
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando  la vista.fxml", e);
        }

    }

}

