package com.HabilidadesProfesionales.Examples.LoginExampleLayoutFXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Wrapper del ejemplo FXML.
 * - NO es Controller.
 * - Su Ãºnico trabajo es cargar el FXML y devolver el Parent root.
 */
public class LoginExampleLayoutFXMLExample {

    public Parent view() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Examples/LoginExampleLayoutFXML/View/login_view.fxml")
            );
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando LoginExampleLayoutFXML", e);
        }
    }
}

