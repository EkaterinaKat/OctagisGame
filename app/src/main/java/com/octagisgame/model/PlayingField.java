package com.octagisgame.model;

import android.graphics.Point;
import android.view.View;

import com.octagisgame.GameActivity;

import java.util.ArrayList;
import java.util.List;

import static com.octagisgame.model.Figure.FIGURE_SIZE;

public class PlayingField {
    private int numberOfColumns;
    private int numberOfRows;
    private Cell[][] cells;
    private Figure fallingFigure;
    private View view;
    private int scoredPoints;
    private final int POINTS_FOR_ONE_ROW = 10;
    private GameActivity activity;
    private FigureCreator figureCreator;

    public PlayingField(GameActivity activity, int numberOfColumns, int numberOfRows) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        this.view = activity.getDrawView();
        this.activity = activity;
        figureCreator = new FigureCreator(numberOfColumns);
    }

    private void initializeFieldWithEmptyCells() {
        cells = new Cell[numberOfColumns][numberOfRows];
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                cells[column][row] = new Cell();
            }
        }
    }

    public void startGame() {
        new Thread(game).start();
    }

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            initializeFieldWithEmptyCells();
            scoredPoints = 0;
            fallingFigure = figureCreator.getRandomFigure();
            while (true) {
                if (figureAbleToDescend()) {
                    fallingFigure.descend();
                    view.invalidate();
                    sleep();
                }else {
                    if (gameOver()) {
                        activity.showGameOverDialog(scoredPoints);
                        break;
                    }
                    finishFalling();
                    deleteFilledRows();
                    fallingFigure = figureCreator.getRandomFigure();
                }
            }
        }
    };

    private boolean figureAbleToDescend() {
        int descendedFigureRow = fallingFigure.getY() + 1;
        for (Point section : getShapeSectionsCoordinates(fallingFigure.getShape(), fallingFigure.getX(), descendedFigureRow)) {

            boolean sectionAboveTop = section.y < 0;
            if (sectionAboveTop)
                break;

            boolean sectionBelowBottom = section.y >= numberOfRows;
            if (sectionBelowBottom)
                return false;

            boolean sectionInFilledCell = cells[section.x][section.y].isFilled();
            if (sectionInFilledCell)
                return false;
        }
        return true;
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean gameOver() {
        for (Point section : getFallingFigureSectionsCoordinates()) {
            boolean sectionAboveTop = section.y < 0;
            if (sectionAboveTop)
                return true;
        }
        return false;
    }

    private void deleteFilledRows() {
        List<Integer> filledRows = getFilledRows();
        increasePoints(filledRows.size());
        for (int row : filledRows) {
            deleteRow(row);
        }
    }

    private void increasePoints(int numberOfRows) {
        double coefficient = 1 + (numberOfRows - 1) / 5.0;
        int a = (int) (numberOfRows * POINTS_FOR_ONE_ROW * coefficient);
        scoredPoints += a;
    }

    private void deleteRow(int targetRow) {
        for (int row = targetRow; row > 0; row--) {
            for (int column = 0; column < numberOfColumns; column++) {
                cells[column][row] = cells[column][row - 1];
            }
        }
        for (int column = 0; column < numberOfColumns; column++) {
            cells[column][0] = new Cell();
        }
    }

    private List<Integer> getFilledRows() {
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

    public int getScoredPoints() {
        return scoredPoints;
    }
}
