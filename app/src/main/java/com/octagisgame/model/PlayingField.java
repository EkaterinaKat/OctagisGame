package com.octagisgame.model;

import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.octagisgame.model.Figure.FIGURE_SIZE;

public class PlayingField {
    private int numberOfColumns;
    private int numberOfRows;
    private Cell[][] cells;
    private Figure fallingFigure;
    private View view;

    public PlayingField(int numberOfColumns, int numberOfRows, View view) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.view = view;
        initializeField();
    }

    private void initializeField() {
        cells = new Cell[numberOfColumns][numberOfRows];
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                cells[column][row] = new Cell();
            }
        }
    }

    public void startGame() {
        game.start();
    }

    private Thread game = new Thread(new Runnable() {
        @Override
        public void run() {
            fallingFigure = FigureCreator.getRandomFigure();
            fallingFigure.descend();
            while (true) {
                view.invalidate();
                if (fallingFigureLanded()) {
                    finishFalling();
                    deleteFilledRows();
                    if (gameOver()) {

                    }
                    fallingFigure = FigureCreator.getRandomFigure();
                }
                fallingFigure.descend();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private boolean gameOver() {
        return false;
    }

    private void deleteFilledRows() {

    }

    private boolean fallingFigureLanded() {
        for (Point sectionCoordinates : getFallingFigureSectionsCoordinates()) {

            boolean figureAtBottom = sectionCoordinates.y == numberOfRows - 1;
            if (figureAtBottom)
                return true;

            boolean figureOnOtherFigure = cells[sectionCoordinates.x][sectionCoordinates.y + 1].isFilled();
            if (figureOnOtherFigure)
                return true;
        }
        return false;
    }

    private void finishFalling() {
        for (Point sectionCoordinates : getFallingFigureSectionsCoordinates()) {
            cells[sectionCoordinates.x][sectionCoordinates.y].makeFilled(fallingFigure.getColor());
        }
    }

    private boolean fallingFigureInCell(int column, int row) {
        for (Point sectionCoordinates : getFallingFigureSectionsCoordinates()) {
            if (sectionCoordinates.x == column && sectionCoordinates.y == row) {
                return true;
            }
        }
        return false;
    }

    private List<Point> getShapeSectionsCoordinates(boolean[][] shape, int shapeX, int shapeY) {
        List<Point> result = new ArrayList<>();
        for (int i = 0; i < FIGURE_SIZE; i++) {
            for (int j = 0; j < FIGURE_SIZE; j++) {
                if (shape[i][j]) {
                    int sectionColumn = getActualColumnNumber(shapeX + j);
                    int sectionRow = shapeY - i;
                    result.add(new Point(sectionColumn, sectionRow));
                }
            }
        }
        return result;
    }

    private List<Point> getFallingFigureSectionsCoordinates() {
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
            view.invalidate();
        }
    }

    public void moveFigureRight() {
        if (figureAbleToMoveRight()) {
            fallingFigure.right();
            view.invalidate();
        }
    }

    public void rotateFigure() {
        if (figureAbleToRotate()) {
            fallingFigure.rotate();
            view.invalidate();
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

    private boolean shapeAcceptable(boolean[][] shape, int posX, int posY) {
        for (Point section : getShapeSectionsCoordinates(shape, posX, posY)) {

            boolean sectionBelowBottom = section.y >= numberOfRows;
            if (sectionBelowBottom)
                return false;

            boolean sectionAboveTop = section.y < 0;
            if (sectionAboveTop)
                return false;

            boolean sectionInFilledCell = cells[section.x][section.y].isFilled();
            if (sectionInFilledCell)
                return false;
        }
        return true;
    }

    public int getCellColour(int column, int row) {
        if (fallingFigureInCell(column, row)) {
            return fallingFigure.getColor();
        }
        return cells[column][row].getColor();
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
