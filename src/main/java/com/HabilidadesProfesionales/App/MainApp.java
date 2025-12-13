package com.HabilidadesProfesionales.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        loadView("/com/marcos/javafx/launcher/menu.fxml");
        stage.setTitle("JavaFX Lab");
        stage.show();
    }

    public static void loadView(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(
                    Main.class.getResource(fxmlPath)
            );
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
