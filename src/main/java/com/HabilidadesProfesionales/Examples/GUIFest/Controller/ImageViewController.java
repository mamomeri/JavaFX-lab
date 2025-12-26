package com.HabilidadesProfesionales.Examples.GUIFest.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class ImageViewController {

    @FXML
    ImageView myImageView;

    @FXML
    Button myButton;

    Image myImage = new Image(getClass().getResourceAsStream("/Images/logo.png"));

    public void displayImage(ActionEvent event){
        myImageView.setImage(myImage);
    }

}

