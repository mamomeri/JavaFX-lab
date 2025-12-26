package com.HabilidadesProfesionales.Examples.ButtonExampleFXML.Controller;

import com.HabilidadesProfesionales.App.IRawExample;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class ButtonFXMLController {

    @FXML
    private Label messageLabel;

    @FXML
    private void onButtonClick() {
        messageLabel.setText("Hola desde FXML");
    }


    public String name() {
        return "ButtonFXMLController";
    }


}

