package com.octagisgame.model;

import java.util.ArrayList;
import java.util.List;

class ShapeParser {
    private int numberOfColumns;

    ShapeParser(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    /* Принимает на вход фигуру и возвращает список её секций */
    List<Section> getSections(Figure figure) {
        return getSections(figure.getShape(), figure.getX(), figure.getY());
    }

    /* Принимает на вход фигуру и возвращает список её секций, каждая из которых опущена на
     * одну клетку вниз */
    List<Section> getDescendedSections(Figure figure) {
        return getSections(figure.getShape(), figure.getX(), figure.getY() + 1);
    }

    /* Принимает на вход форму и координаты верхней левой клеточки формы на поле и возвращает
     * список секций формы */
    List<Section> getSections(Shape shape, int x, int y) {
        List<Section> result = new ArrayList<>();
        for (int i = 0; i < shape.getVerticalSize(); i++) {
            for (int j = 0; j < shape.getHorizontalSize(); j++) {
                if (shape.hasSectionInCell(i, j)) {
                    int sectionColumn = getActualColumnNumber(x + j);
                    int sectionRow = y - i;
                    result.add(new Section(sectionColumn, sectionRow));
                }
            }
        }
        return result;
    }

    private int getActualColumnNumber(int columnNum) {
        while (columnNum < 0)
            columnNum += numberOfColumns;
        columnNum %= numberOfColumns;
        return columnNum;
    }
}
