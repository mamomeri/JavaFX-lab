package com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CommunicationControllersController2 {

    @FXML
    Label nameLabel;
    public void showName(String userName){
        nameLabel.setText("Hello! "+ userName);
    }
}

