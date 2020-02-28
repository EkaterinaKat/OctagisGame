package com.octagisgame.controller;

import com.octagisgame.activities.GameActivity;
import com.octagisgame.dialogs.GameOverDialog;
import com.octagisgame.model.FigureCreator;
import com.octagisgame.model.PlayingField;

import java.util.List;

public class GameProcess {
    private final int POINTS_FOR_ONE_ROW = 10;
    private final int STANDARD_TIME_INTERVAL = 300;
    private final int REDUCED_TIME_INTERVAL = 20;
    private int timeInterval;
    private int scoredPoints;
    private GameActivity activity;
    private FigureCreator figureCreator;
    private boolean gamePaused;
    private PlayingField field;

    public GameProcess(GameActivity activity, PlayingField field) {
        this.field = field;
        this.activity = activity;
        figureCreator = new FigureCreator(field.getNumberOfColumns());
        timeInterval = STANDARD_TIME_INTERVAL;
    }

    public void start() {
        new Thread(game).start();
    }

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            field.initializeWithEmptyCells();
            scoredPoints = 0;
            gamePaused = false;
            generateNextFigure();
            while (true) {
                if (!gamePaused) {
                    field.descentFigure();
                    sleep();
                    if(field.figureLanded()){
                        timeInterval = STANDARD_TIME_INTERVAL;
                        if (field.figureAboveTop()) {
                            showGameOverDialog(scoredPoints);
                            break;
                        }
                        field.finishFalling();
                        deleteFilledRows();
                        generateNextFigure();
                    }
                }
            }
        }
    };

    public void moveFigureLeft() {
        field.moveFigureLeft();
    }

    public void moveFigureRight() {
        field.moveFigureRight();
    }

    public void rotateFigure() {
        field.rotateFigure();
    }

    public void speedUpFalling() {
        timeInterval = REDUCED_TIME_INTERVAL;
    }

    private void generateNextFigure() {
        field.setFallingFigure(figureCreator.getRandomFigure());
    }

    private void deleteFilledRows() {
        List<Integer> filledRows = field.getFilledRows();
        increasePoints(filledRows.size());
        for (int row : filledRows) {
            field.deleteRow(row);
        }
    }

    private void increasePoints(int numberOfRows) {
        double coefficient = 1 + (numberOfRows - 1) / 5.0;
        int a = (int) (numberOfRows * POINTS_FOR_ONE_ROW * coefficient);
        scoredPoints += a;
    }

    private void showGameOverDialog(int scoredPoints) {
        GameOverDialog gameOverDialog = new GameOverDialog(activity, scoredPoints);
        gameOverDialog.show(activity.getSupportFragmentManager(), "gameOverDialog");
    }

    private void sleep() {
        try {
            Thread.sleep(timeInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getScoredPoints() {
        return scoredPoints;
    }

    public void setOnPause() {
        gamePaused = true;
    }

    public void continueGame() {
        gamePaused = false;
    }

    public boolean isPaused() {
        return gamePaused;
    }
}
