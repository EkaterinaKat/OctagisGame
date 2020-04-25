package com.octagisgame.view.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.model.PlayingField;
import com.octagisgame.view.painttuners.PaintTuner;

abstract public class GameDrawer {
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;
    PaintTuner paintTuner;

    GameDrawer(Point displaySize, PaintTuner paintTuner) {
        this.paintTuner = paintTuner;
        PlayingField field = Game.getInstance().getField();
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
        String scoredPoints = String.valueOf(Game.getInstance().getScoredPoints());
        canvas.drawText(scoredPoints, 50, 50, paintTuner.getTextPaint());
    }

    abstract void drawCell(int column, int row, Canvas canvas);
}
