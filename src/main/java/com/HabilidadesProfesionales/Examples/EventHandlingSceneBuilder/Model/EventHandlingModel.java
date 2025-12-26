package com.HabilidadesProfesionales.Examples.EventHandlingSceneBuilder.Model;

public class EventHandlingModel {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private double x = 301;   // posiciÃ³n lÃ³gica inicial (ajÃºstalo a tu FXML)
    private double y = 200;

    private Direction lastDirection = Direction.UP;

    private final double step = 30;

    public void move(Direction dir) {
        lastDirection = dir;
        switch (dir) {
            case UP -> y -= step;
            case DOWN -> y += step;
            case LEFT -> x -= step;
            case RIGHT -> x += step;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public Direction getLastDirection() { return lastDirection; }
}

