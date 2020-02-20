package com.octagisgame.model;

class Figure {
    static final int FIGURE_SIZE = 4;
    private boolean[][] shape;
    private int color;
    /* Горизонтальная координата нижней левой клеточки фигуры.
     * Горизонтальная ось направлена слева направо */
    private int x;
    /* Вертикальная координата нижней левой клеточки фигуры.
     * Вертикальная ось направлена сверху вниз */
    private int y;

    Figure(boolean[][] shape, int color, int initialHorizontalPosition) {
        this.shape = shape;
        this.color = color;
        x = initialHorizontalPosition;
        y = 0;
    }

    void rotate() {
        shape = getRotatedShape();
    }

    boolean[][] getRotatedShape() {
        boolean[][] result = new boolean[FIGURE_SIZE][FIGURE_SIZE];
        for (int i = 0; i < FIGURE_SIZE; i++) {
            for (int j = 0; j < FIGURE_SIZE; j++) {
                result[FIGURE_SIZE-1-j][i] = shape [i][j];
            }
        }
        return result;
    }

    void descend() {
        y++;
    }

    void left(){
        x--;
    }

    void right(){
        x++;
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
}
