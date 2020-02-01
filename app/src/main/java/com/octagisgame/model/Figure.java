package com.octagisgame.model;

public class Figure {
    private boolean[][] shape;
    private int color;
    //горизонтальная координата направлена слева направо, вертикальная сверху вниз
    //координаты нижней левой клеточки фигуры
    private int x;
    private int y;

    Figure(boolean[][] shape, int color) {
        this.shape = shape;
        this.color = color;
        x = 0;
        y = 0;
    }

    void descend() {
        y++;
    }

    boolean[][] getShape() {
        return shape;
    }

    int getColor() {
        return color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void rotate() {

    }
}
