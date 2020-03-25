package com.octagisgame.model;

public class Shape {
    private boolean[][] array;

    public Shape(boolean[][] array) {
        this.array = array;
    }

    /* Принимает на вход относительные координаты ячейки и возвращает true если в этой ячейке
     * находится секция формы */
    boolean hasSectionInCell(int x, int y) {
        return array[x][y];
    }

    Shape getRotatedShape() {
        boolean[][] rotatedArray = new boolean[getHorizontalSize()][getVerticalSize()];
        for (int i = 0; i < getVerticalSize(); i++) {
            for (int j = 0; j < getHorizontalSize(); j++) {
                rotatedArray[getHorizontalSize() - 1 - j][i] = array[i][j];
            }
        }
        return new Shape(rotatedArray);
    }

    int getHorizontalSize() {
        return array[0].length;
    }

    int getVerticalSize() {
        return array.length;
    }
}
