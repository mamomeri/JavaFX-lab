package com.HabilidadesProfesionales.Examples.Animations.Controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AnimationsController implements Initializable {

    @FXML
    private ImageView myImage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Translation
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        translate.setNode(myImage);
        translate.setByX(250);
        translate.play();
        //Rotation
        RotateTransition rotate = new RotateTransition();
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setAutoReverse(true);
        rotate.setNode(myImage);
        rotate.setByAngle(180);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.play();
        //fade
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.setNode(myImage);
        fade.setInterpolator(Interpolator.LINEAR);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
        //scale
        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setAutoReverse(true);
        scale.setNode(myImage);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(2);
        scale.setByY(2);
        scale.play();

    }
}
