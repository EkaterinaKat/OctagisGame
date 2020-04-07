package com.octagisgame.view.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.model.Cell;
import com.octagisgame.model.PlayingField;
import com.octagisgame.view.painttuners.PaintTuner;

abstract public class FieldDrawer {
    Game game;
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;
    PaintTuner paintTuner;

    FieldDrawer(Game game, Point displaySize, PaintTuner paintTuner) {
        this.paintTuner = paintTuner;
        this.game = game;
        PlayingField field = game.getField();
        numberOfColumns = field.getNumberOfColumns();
        numberOfRows = field.getNumberOfRows();
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawField(canvas);
        printScoredPoints(canvas);
    }

    private void drawField(Canvas canvas) {
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                drawCell(column, row, canvas);
            }
        }
    }

    private void printScoredPoints(Canvas canvas) {
        String scoredPoints = String.valueOf(game.getScoredPoints());
        canvas.drawText(scoredPoints, 50, 50, paintTuner.getTextPaint());
    }

    abstract void drawCell(int column, int row, Canvas canvas);
}
