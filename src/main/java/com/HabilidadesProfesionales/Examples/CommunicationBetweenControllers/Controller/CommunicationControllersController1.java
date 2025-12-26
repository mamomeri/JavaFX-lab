package com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CommunicationControllersController1 {

    @FXML
    Button loginButton;
    @FXML
    TextField textFieldLoging;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onLogin(ActionEvent event) throws IOException {
        String nameUser = textFieldLoging.getText();

        if (nameUser == null || nameUser.trim().isEmpty()) {
            return; // O muestra un alerta
        }

        // Cargar la nueva vista
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/Examples/CommunicationBetweenControllers/View/Scene 2.fxml")
        );
        root = loader.load();

        // Pasar datos al controlador 2
        CommunicationControllersController2 controller2 = loader.getController();
        controller2.showName(nameUser);

        // Obtener el Stage actual desde el evento
        Node sourceNode = (Node) event.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        // Crear y mostrar la nueva escena en el stage ACTUAL
        scene = new Scene(root);
        currentStage.setScene(scene);  // âœ… USAR currentStage
        currentStage.show();           // âœ… USAR currentStage
    }

}

