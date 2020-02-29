package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.stylers.Styler;

abstract public class FieldDrawer {
    final int TEXT_SIZE = 40;
    Path path;
    Paint paint;
    Game game;
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;
    Styler styler;

    FieldDrawer(Game game, Point displaySize, Styler styler) {
        this.styler = styler;
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.game = game;
        numberOfColumns = game.getNumberOfColumns();
        numberOfRows = game.getNumberOfRows();
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawField(canvas);
    }

    private void drawField(Canvas canvas) {
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                drawCell(column, row, canvas);
            }
        }
    }

//    private void printScoredPoints(Canvas canvas) {   //todo сделать что-то с этим
//        String scoredPoints = String.valueOf(field.getScoredPoints());
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(TEXT_SIZE);
//        canvas.drawText(scoredPoints, 50, 50, paint);
//    }

    abstract void drawCell(int column, int row, Canvas canvas);
}
