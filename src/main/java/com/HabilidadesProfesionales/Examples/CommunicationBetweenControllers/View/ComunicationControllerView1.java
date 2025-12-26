package com.HabilidadesProfesionales.Examples.CommunicationBetweenControllers.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ComunicationControllerView1 {


    public Parent load() {
        try {
            // 1. Crear instancia con la ruta
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Examples/CommunicationBetweenControllers/View/Scene 1.fxml")
            );

            // 2. Cargar el contenido (NO volver a llamar load() en la URL)
            Parent root = loader.load();

            // 3. Obtener el controlador para pasar datos
            // TuController controller = loader.getController();
            // controller.setDatos(...);

            return root;
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar Scene 1.fxml", e);
        }
    }
}

