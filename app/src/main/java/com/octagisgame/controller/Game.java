package com.octagisgame.controller;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;
import com.octagisgame.activities.GameActivity;
import com.octagisgame.drawers.RowDeletionAnimator;
import com.octagisgame.model.PlayingField;
import com.octagisgame.model.ScoreTable;

import java.util.List;

public class Game {
    private final int POINTS_FOR_ONE_ROW = 10;
    private final int STANDARD_TIME_INTERVAL = 500;
    private final int REDUCED_TIME_INTERVAL = 20;
    private int timeInterval;
    private int scoredPoints;
    private GameActivity activity;
    private FigureCreator figureCreator;
    private boolean gamePaused;
    private PlayingField field;
    private ScoreTable scoreTable;
    private RowDeletionAnimator animator;

    public Game(GameActivity activity, PlayingField field) {
        this.field = field;
        this.activity = activity;
        figureCreator = new FigureCreator(activity);
        scoreTable = ScoreTable.getInstance();
        animator = new RowDeletionAnimator(field);
    }

    public void start() {
        new Thread(game).start();
    }

    private Runnable game = new Runnable() {
        @Override
        public void run() {
            field.initializeWithEmptyCells();
            scoredPoints = 0;
            timeInterval = STANDARD_TIME_INTERVAL;
            gamePaused = false;
            generateNextFigure();
            while (true) {
                if (!gamePaused) {
                    field.descentFigure();
                    sleep();
                    if (field.figureLanded()) {
                        timeInterval = STANDARD_TIME_INTERVAL;
                        if (field.figureAboveTop()) {
                            saveScore();
                            activity.showGameOverDialog(scoredPoints);
                            break;
                        }
                        field.finishFalling();
                        generateNextFigure();
                        deleteFilledRows();
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
        field.setNewFallingFigure(figureCreator.getRandomFigure());
    }

    private void deleteFilledRows() {
        List<Integer> filledRows = field.getFilledRows();
        if (filledRows.size() > 0) {
            animator.animateRowDeletion(filledRows);
            increasePoints(filledRows.size());
            for (int row : filledRows) {
                field.deleteRow(row);
            }
        }
    }

    private void increasePoints(int numberOfRows) {
        double coefficient = 1 + (numberOfRows - 1) / 5.0;
        int a = (int) (numberOfRows * POINTS_FOR_ONE_ROW * coefficient);
        scoredPoints += a;
    }

    private void saveScore() {
        if (scoredPoints > 0) {
            scoreTable.addScore(scoredPoints);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(timeInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCellColour(int column, int row) {
        if (field.fallingFigureInCell(column, row)) {
            return field.getFallingFigureColor();
        }
        if (field.figureShadowInCell(column, row)) {
            return ContextCompat.getColor(activity, R.color.shadowColor);
        }
        if (field.cellIsEmpty(column, row)){
            return Color.WHITE;
        }
        return field.getCellColour(column, row);
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

    public int getNumberOfColumns() {
        return field.getNumberOfColumns();
    }

    public int getNumberOfRows() {
        return field.getNumberOfRows();
    }
}
