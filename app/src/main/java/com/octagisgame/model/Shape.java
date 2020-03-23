package com.octagisgame.model;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    private boolean[][] array;

    public Shape(boolean[][] array) {
        this.array = array;
    }

    /* Принимает на вход координаты верхней левой клеточки фигуры на поле и возвращает
     * список координат всех секций фигуры */
    List<ShapeSectionCoordinates> getSectionsCoordinates(int x, int y) {
        List<ShapeSectionCoordinates> result = new ArrayList<>();
        for (int i = 0; i < getVerticalSize(); i++) {
            for (int j = 0; j < getHorizontalSize(); j++) {
                if (array[i][j]) {
                    int sectionColumn = getActualColumnNumber(x + j, 15); //todo возможно нужно использовать этот метод в другом месте
                    int sectionRow = y - i;
                    result.add(new ShapeSectionCoordinates(sectionColumn, sectionRow));
                }
            }
        }
        return result;
    }

    private int getActualColumnNumber(int columnNum, int numberOfColumns) {
        while (columnNum < 0)
            columnNum += numberOfColumns;
        columnNum %= numberOfColumns;
        return columnNum;
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

    private int getVerticalSize() {
        return array.length;
    }
}
