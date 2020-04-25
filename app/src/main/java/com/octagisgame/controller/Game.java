package com.octagisgame.controller;

import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;
import com.octagisgame.activities.GameActivity;
import com.octagisgame.model.PlayingField;
import com.octagisgame.model.ScoreTable;
import com.octagisgame.services.SoundManager;
import com.octagisgame.view.RowDeletionAnimator;

import java.util.List;

public class Game {
    private static Game instance;
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

    public static Game getInstance() {
        return instance;
    }

    public static Game create(GameActivity activity, PlayingField field) {
        if (instance == null) {
            instance = new Game(activity, field);
        }
        return instance;
    }

    private Game(GameActivity activity, PlayingField field) {
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
            field.fillWithEmptyCells();
            scoredPoints = 0;
            timeInterval = STANDARD_TIME_INTERVAL;
            gamePaused = false;
            generateNextFigure();
            while (true) {
                if (!gamePaused) {
                    field.descentFigure();
                    waitForSpeedUpOrNextDescent();
                    if (field.figureLanded()) {
                        if (field.figureAboveTop()) {
                            SoundManager.getInstance().playGameOverSound();
                            saveScore();
                            activity.showGameOverDialog(scoredPoints);
                            break;
                        }
                        field.finishFalling();
                        deleteFilledRows();
                        generateNextFigure();
                        timeInterval = STANDARD_TIME_INTERVAL;
                    }
                }
            }
        }
    };

    public void moveFigureLeft() {
        if (field.moveFigureLeft()) {
            SoundManager.getInstance().playMoveSound();
        }
    }

    public void moveFigureRight() {
        if (field.moveFigureRight()) {
            SoundManager.getInstance().playMoveSound();
        }
    }

    public void rotateFigure() {
        if (field.rotateFigure()) {
            SoundManager.getInstance().playMoveSound();
        }
    }

    public synchronized void speedUpFalling() {
        if (timeInterval == STANDARD_TIME_INTERVAL) {
            timeInterval = REDUCED_TIME_INTERVAL;
            notify();
            SoundManager.getInstance().playSpeedUpSound();
        }
    }

    private void generateNextFigure() {
        field.setNewFallingFigure(figureCreator.getRandomFigure());
    }

    private void deleteFilledRows() {
        List<Integer> filledRows = field.getFilledRows();
        if (filledRows.size() > 0) {
            SoundManager.getInstance().playRowDeletionSound();
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

    private synchronized void waitForSpeedUpOrNextDescent() {
        try {
            wait(timeInterval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCellColour(int column, int row) {
        if (!field.cellIsEmpty(column, row)) {
            return field.getCellColour(column, row);
        }
        if (field.fallingFigureInCell(column, row)) {
            return field.getFallingFigureColor();
        }
        if (field.figureShadowInCell(column, row)) {
            return ContextCompat.getColor(activity, R.color.shadowColor);
        }
        return Color.WHITE;
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

    public PlayingField getField() {
        return field;
    }
}
