package com.HabilidadesProfesionales.Examples.SwitchScenes.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;   // âœ…

import java.io.IOException;
import java.net.URL;

public class SwitchScenesController1 {

    // No es obligatorio guardarlos como fields, pero si quieres inspeccionarlos/debug, ok.
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Cambia a Scene2.fxml cuando se dispara un evento (ej: click de botÃ³n).
     */
    public void switchToScene2(ActionEvent event) throws IOException {

        // 1) Ubicamos el archivo FXML dentro de src/main/resources
        //    Ruta absoluta en resources: /Examples/SwitchScenes/View/Scene2.fxml
        URL fxmlLocation = getClass().getResource("/Examples/SwitchScenes/View/Scene2.fxml");

        // Si la ruta estÃ¡ mal, esto evita un NullPointerException con un mensaje claro
        if (fxmlLocation == null) {
            throw new IOException("No se encontrÃ³ el FXML: /Examples/SwitchScenes/View/Scene2.fxml");
        }

        // 2) Cargamos el FXML -> nos devuelve el nodo raÃ­z (root) definido en el archivo
        root = FXMLLoader.load(fxmlLocation);

        // 3) De ActionEvent sacamos la fuente (source), por ejemplo un Button.
        //    Ese botÃ³n es un Node dentro de una Scene, y esa Scene pertenece a un Stage (ventana).
        Node sourceNode = (Node) event.getSource();
        stage = (Stage) sourceNode.getScene().getWindow();

        // 4) Creamos una nueva Scene con el root reciÃ©n cargado
        //    (Esto significa: estamos reemplazando TODO el contenido de la ventana)
        scene = new Scene(root);

        // 5) Aplicamos la nueva escena a la ventana
        stage.setScene(scene);

        // 6) Mostramos la ventana (si ya estaba visible, esto no hace daÃ±o)
        stage.show();
    }
}

