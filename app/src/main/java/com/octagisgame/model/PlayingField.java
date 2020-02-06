package com.octagisgame.model;

import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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

    private void initializeField(){
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
            while (true) {
                view.invalidate();
                fallingFigure.descend();
                if (fallingFigureLanded()) {
                    deleteFilledRows();
                    if (gameOver()) {

                    }
                    endFalling();
                }
                try {
                    Thread.sleep(500);
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
        for(Point sectionCoordinates: getFallingFigureSectionsCoordinates()){
            if(sectionCoordinates.y==numberOfRows-1){
                return true;
            }else if (cells[sectionCoordinates.x][sectionCoordinates.y+1].isFilled()){
                return true;
            }
        }
        return false;
    }

    private void endFalling() {
        for(Point sectionCoordinates: getFallingFigureSectionsCoordinates()){
            cells[sectionCoordinates.x][sectionCoordinates.y].makeFilled(fallingFigure.getColor());
        }
        fallingFigure = FigureCreator.getRandomFigure();
    }

    private boolean fallingFigureInCell(int column, int row){
        for(Point sectionCoordinates: getFallingFigureSectionsCoordinates()){
            if(sectionCoordinates.x==column && sectionCoordinates.y == row){
                return true;
            }
        }
        return false;
    }

    private List<Point> getFallingFigureSectionsCoordinates(){
        List<Point> result = new ArrayList<>();
        int figureSize = 4;
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                if(fallingFigure.getShape()[i][j]){
                    int x = getActualColumnNumber(fallingFigure.getX()+j);
                    int y = fallingFigure.getY()-i;
                    result.add(new Point(x, y));
                }
            }
        }
        return result;
    }

    private int getActualColumnNumber(int columnNum){
        while (columnNum<0)
            columnNum+=numberOfColumns;
        columnNum%=numberOfColumns;
        return columnNum;
    }

    public void left(){
        fallingFigure.left();
        view.invalidate();
    }

    public void right(){
        fallingFigure.right();
        view.invalidate();
    }

    public void rotateFigure(){
        if(figureAbleToRotate()){
            fallingFigure.rotate();
            view.invalidate();
        }
    }

    private boolean figureAbleToRotate(){
        //можно сделатть пробный поворот "в уме" посмотреть если всё норм то рил поворачивать
        return false;
    }

    public int getCellColour(int column, int row) {
        if(fallingFigureInCell(column, row)){
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
