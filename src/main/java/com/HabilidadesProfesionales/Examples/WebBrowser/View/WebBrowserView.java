package com.HabilidadesProfesionales.Examples.WebBrowser.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class WebBrowserView {
    public Parent load() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Examples/WebBrowser/View/WebBrowserScene.fxml"));
            Parent root = loader.load();
            return root;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
