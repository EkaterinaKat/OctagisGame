package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.model.Cell;
import com.octagisgame.model.PlayingField;
import com.octagisgame.stylers.Styler;

abstract public class FieldDrawer {
    final int TEXT_SIZE = 40;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Game game;
    Cell[][] cells;
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;
    Styler styler;

    FieldDrawer(Game game, Point displaySize, Styler styler) {
        this.styler = styler;
        this.game = game;
        PlayingField field = game.getField();
        numberOfColumns = field.getNumberOfColumns();
        numberOfRows = field.getNumberOfRows();
        cells = field.getCells();
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
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText(scoredPoints, 50, 50, paint);
    }

    abstract void drawCell(int column, int row, Canvas canvas);
}
