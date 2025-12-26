package com.HabilidadesProfesionales.Examples.LoginExampleLayoutFXML.Controller;

import com.HabilidadesProfesionales.Utils.FontUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class LoginController {

    @FXML private StackPane root;
    @FXML private ImageView bgImage;
    @FXML private Region darkOverlay;

    @FXML private Label brandLabel;
    @FXML private Label titleLabel;

    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private Button loginBtn;
    @FXML private Label status;

    @FXML private ImageView userIcon;

    @FXML
    private void initialize() {

        // ===== Fondo =====
        bgImage.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/Images/campo.jpeg"),
                "No encuentro /Images/campo.jpeg en resources"
        )));
        bgImage.fitWidthProperty().bind(root.widthProperty());
        bgImage.fitHeightProperty().bind(root.heightProperty());
        darkOverlay.prefWidthProperty().bind(root.widthProperty());
        darkOverlay.prefHeightProperty().bind(root.heightProperty());

        // ===== Icono usuario =====
        userIcon.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/Images/login.png"),
                "No encuentro /Images/login.png en resources"
        )));
        userIcon.setClip(new Circle(55, 55, 55)); // si el icono es 110x110

        // ===== Fuente custom SOLO vÃ­a FontUtils =====
        final String FONT_PATH = "/Fonts/Lobster/Lobster-Regular.ttf";
        FontUtils.applyTo(
                brandLabel,
                FONT_PATH,
                34.0,
                "Verdana",
                true
        );

        loginBtn.setMaxWidth(Double.MAX_VALUE);
    }

    @FXML
    private void onLogin() {
        new Alert(Alert.AlertType.INFORMATION, "Login demo âœ…").showAndWait();
    }
}

