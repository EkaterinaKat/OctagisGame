package com.octagisgame.model;

public class Figure {
    private Shape shape;
    private int color;
    /* Горизонтальная координата нижней левой клеточки фигуры.
     * Горизонтальная ось направлена слева направо */
    private int x;
    /* Вертикальная координата нижней левой клеточки фигуры.
     * Вертикальная ось направлена сверху вниз */
    private int y;

    public Figure(Shape shape, int color) {
        this.shape = shape;
        this.color = color;
        x = 0;
        y = 0;
    }

    void setInitialHorizontalPos(int numberOfColumns) {
        x = numberOfColumns / 2 - shape.getHorizontalSize() / 2;
    }

    public Figure copy() {
        return new Figure(shape, color);
    }

    void rotate() {
        shape = getRotatedShape();
    }

    Shape getRotatedShape() {
        return shape.getRotatedShape();
    }

    void descend() {
        y++;
    }

    void left() {
        x--;
    }

    void right() {
        x++;
    }

    Shape getShape() {
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
}
