package com.HabilidadesProfesionales.Examples.LogOutExitButton.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LogOutExitButtonController {
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Button logoutButton;

    Stage stage;

    public void onLogout(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Estas a punto de desloguearte");
        alert.setContentText("Quieres guardar antes de salir?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage)scenePane.getScene().getWindow();
            System.out.println("You succesfully logged out!");
            stage.close();
        }



    }
}

