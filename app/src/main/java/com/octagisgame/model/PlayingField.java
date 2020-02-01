package com.octagisgame.model;

import android.view.View;

public class PlayingField {
    private final int FIGURE_SIZE = 4;
    private int numberOfColumns;
    private int numberOfRows;
    private Cell[][] cells;
    private Figure fallingFigure;
    private View view;

    public PlayingField(int numberOfColumns, int numberOfRows, View view) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
        cells = new Cell[numberOfColumns][numberOfRows];
        this.view = view;
        initializeField();
    }

    private void initializeField(){
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
            generateNewFallingFigure();
            while (true) {
                view.invalidate();
                fallingFigure.descend();
                if (fallingFigureLanded()) {
                    deleteFilledRows();
                    if (gameOver()) {

                    }
                    generateNewFallingFigure();
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private boolean fallingFigureLanded() {
        return false;
    }

    private boolean gameOver() {
        return false;
    }

    private void deleteFilledRows() {

    }

    private void generateNewFallingFigure() {
        fallingFigure = FigureCreator.getRandomFigure();
    }

    private boolean fallingFigureInCell(int column, int row){
        for (int i = 0; i < FIGURE_SIZE; i++) {
            for (int j = 0; j < FIGURE_SIZE; j++) {
                if(fallingFigure.getShape()[i][j]){
                    if(fallingFigure.getX()+j==column && fallingFigure.getY()-i==row){
                        return true;
                    }
                }
            }
        }
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
