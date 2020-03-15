package com.octagisgame.model;

import java.util.ArrayList;
import java.util.List;

import static com.octagisgame.model.Figure.FIGURE_SIZE;

public class PlayingField {
    private int numberOfColumns;
    private int numberOfRows;
    private Cell[][] cells;
    private Figure fallingFigure;
    private int initialFigureHorizontalPos;

    public PlayingField(int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        initialFigureHorizontalPos = numberOfColumns / 2 - FIGURE_SIZE / 2;
    }

    public void initializeWithEmptyCells() {
        cells = new Cell[numberOfColumns][numberOfRows];
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                cells[column][row] = new Cell();
            }
        }
    }

    public boolean figureLanded() {
        return !figureAbleToDescend();
    }

    public boolean figureAboveTop() {
        for (FigureSection section : getFallingFigureSectionsCoordinates()) {
            boolean sectionAboveTop = section.getRow() < 0;
            if (sectionAboveTop)
                return true;
        }
        return false;
    }

    public List<Integer> getFilledRows() {
        List<Integer> result = new ArrayList<>();
        for (int row = 0; row < numberOfRows; row++) {
            boolean rowFilled = true;
            for (int column = 0; column < numberOfColumns; column++) {
                if (!cells[column][row].isFilled())
                    rowFilled = false;
            }
            if (rowFilled)
                result.add(row);
        }
        return result;
    }

    public void deleteRow(int targetRow) {
        for (int row = targetRow; row > 0; row--) {
            for (int column = 0; column < numberOfColumns; column++) {
                cells[column][row] = cells[column][row - 1];
            }
        }
        for (int column = 0; column < numberOfColumns; column++) {
            cells[column][0] = new Cell();
        }
    }

    public void finishFalling() {
        for (FigureSection section : getFallingFigureSectionsCoordinates()) {
            cells[section.getColumn()][section.getRow()].makeFilled(fallingFigure.getColor());
        }
    }

    private List<FigureSection> getFallingFigureSectionsCoordinates() {
        return getShapeSectionsCoordinates(fallingFigure.getShape(), fallingFigure.getX(), fallingFigure.getY());
    }

    private int getActualColumnNumber(int columnNum) {
        while (columnNum < 0)
            columnNum += numberOfColumns;
        columnNum %= numberOfColumns;
        return columnNum;
    }

    public void moveFigureLeft() {
        if (figureAbleToMoveLeft()) {
            fallingFigure.left();
        }
    }

    public void moveFigureRight() {
        if (figureAbleToMoveRight()) {
            fallingFigure.right();
        }
    }

    public void rotateFigure() {
        if (figureAbleToRotate()) {
            fallingFigure.rotate();
        }
    }

    public void descentFigure() {
        if (figureAbleToDescend()) {
            fallingFigure.descend();
        }
    }

    private boolean figureAbleToRotate() {
        boolean[][] rotatedShape = fallingFigure.getRotatedShape();
        return shapeAcceptable(rotatedShape, fallingFigure.getX(), fallingFigure.getY());
    }

    private boolean figureAbleToMoveLeft() {
        int movedFigureColumn = getActualColumnNumber(fallingFigure.getX() - 1);
        return shapeAcceptable(fallingFigure.getShape(), movedFigureColumn, fallingFigure.getY());
    }

    private boolean figureAbleToMoveRight() {
        int movedFigureColumn = getActualColumnNumber(fallingFigure.getX() + 1);
        return shapeAcceptable(fallingFigure.getShape(), movedFigureColumn, fallingFigure.getY());
    }

    private boolean figureAbleToDescend() {
        int descendedFigureRow = fallingFigure.getY() + 1;
        for (FigureSection section : getShapeSectionsCoordinates(fallingFigure.getShape(), fallingFigure.getX(), descendedFigureRow)) {

            boolean sectionAboveTop = section.getRow() < 0;
            if (sectionAboveTop)
                break;

            boolean sectionBelowBottom = section.getRow() >= numberOfRows;
            if (sectionBelowBottom)
                return false;

            boolean sectionInFilledCell = cells[section.getColumn()][section.getRow()].isFilled();
            if (sectionInFilledCell)
                return false;
        }
        return true;
    }

    private boolean shapeAcceptable(boolean[][] shape, int posX, int posY) {
        for (FigureSection section : getShapeSectionsCoordinates(shape, posX, posY)) {

            boolean sectionBelowBottom = section.getRow() >= numberOfRows;
            if (sectionBelowBottom)
                return false;

            boolean sectionAboveTop = section.getRow() < 0;
            if (sectionAboveTop)
                return false;

            boolean sectionInFilledCell = cells[section.getColumn()][section.getRow()].isFilled();
            if (sectionInFilledCell)
                return false;
        }
        return true;
    }

    public boolean fallingFigureInCell(int column, int row) {
        for (FigureSection section : getFallingFigureSectionsCoordinates()) {
            if (section.getColumn() == column && section.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    public boolean figureShadowInCell(int column, int row) {
        for (FigureSection section : getFigureShadowCoordinates()) {
            if (section.getColumn() == column && section.getRow() == row) {
                return true;
            }
        }
        return false;
    }

    private List<FigureSection> getFigureShadowCoordinates() {
        boolean[][] projectionShape = fallingFigure.getShape();
        int projectionY = fallingFigure.getY();
        int projectionX = fallingFigure.getX();
        while (shapeAcceptable(projectionShape, projectionX, projectionY + 1)) {
            projectionY += 1;
        }
        return getShapeSectionsCoordinates(projectionShape, projectionX, projectionY);
    }

    private List<FigureSection> getShapeSectionsCoordinates(boolean[][] shape, int shapeX, int shapeY) {
        List<FigureSection> result = new ArrayList<>();
        for (int i = 0; i < FIGURE_SIZE; i++) {
            for (int j = 0; j < FIGURE_SIZE; j++) {
                if (shape[i][j]) {
                    int sectionColumn = getActualColumnNumber(shapeX + j);
                    int sectionRow = shapeY - i;
                    result.add(new FigureSection(sectionColumn, sectionRow));
                }
            }
        }
        return result;
    }

    public void setNewFallingFigure(Figure figure) {
        fallingFigure = figure;
        fallingFigure.setHorizontalPos(initialFigureHorizontalPos);
    }

    public List<Cell> getCellsFromRow(int row) {
        List<Cell> cellsFromRow = new ArrayList<>();
        for (int column = 0; column < numberOfColumns; column++) {
            cellsFromRow.add(cells[column][row]);
        }
        return cellsFromRow;
    }

    public int getFallingFigureColor() {
        return fallingFigure.getColor();
    }

    public int getCellColour(int column, int row) {
        return cells[column][row].getColor();
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
