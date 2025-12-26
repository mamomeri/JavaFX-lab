package com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.Controller;

import com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.Model.EventHandlingModel;
import com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.Model.EventHandlingModel.Direction;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class EventHandlingController {

    private final EventHandlingModel model = new EventHandlingModel();

    @FXML private Circle mycircle;
    @FXML private Button buttonUp;
    @FXML private Button buttonDown;
    @FXML private Button buttonLeft;
    @FXML private Button buttonRight;

    private double uiX; // estado visual (translate acumulado)
    private double uiY;

    private static final double ANIM_MS = 120;

    @FXML
    private void initialize() {
        // para que los botones siempre sean clickeables
        if (buttonUp != null) buttonUp.toFront();
        if (buttonDown != null) buttonDown.toFront();
        if (buttonLeft != null) buttonLeft.toFront();
        if (buttonRight != null) buttonRight.toFront();

        // sincroniza UI con Model (punto de inicio)
        uiX = 0;
        uiY = 0;
        render();
    }

    // ===== eventos =====
    public void up(ActionEvent e)    { tap(buttonUp);    move(Direction.UP); }
    public void down(ActionEvent e)  { tap(buttonDown);  move(Direction.DOWN); }
    public void left(ActionEvent e)  { tap(buttonLeft);  move(Direction.LEFT); }
    public void right(ActionEvent e) { tap(buttonRight); move(Direction.RIGHT); }

    // ===== puente Controller =====
    private void move(Direction dir) {
        double oldX = model.getX();
        double oldY = model.getY();

        model.move(dir);

        double dx = model.getX() - oldX;
        double dy = model.getY() - oldY;

        animateMove(dx, dy);
        render(); // color u otras cosas
    }

    // ===== render (UI desde estado del Model) =====
    private void render() {
        mycircle.setFill(colorFor(model.getLastDirection()));
    }

    private Color colorFor(Direction d) {
        return switch (d) {
            case UP -> Color.DODGERBLUE;
            case DOWN -> Color.ORANGERED;
            case LEFT -> Color.LIMEGREEN;
            case RIGHT -> Color.GOLD;
        };
    }

    // ===== animaciones (UI puro) =====
    private void animateMove(double dx, double dy) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(ANIM_MS), mycircle);
        tt.setByX(dx);
        tt.setByY(dy);
        tt.play();
    }

    private void tap(Button b) {
        if (b == null) return;

        b.setEffect(new DropShadow(15, Color.DODGERBLUE));

        ScaleTransition st = new ScaleTransition(Duration.millis(120), b);
        st.setFromX(1.0); st.setFromY(1.0);
        st.setToX(1.25);  st.setToY(1.25);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.setOnFinished(ev -> b.setEffect(null));
        st.play();
    }
}

