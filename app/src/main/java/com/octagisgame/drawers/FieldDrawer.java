package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

abstract public class FieldDrawer {
    private final int TEXT_SIZE = 40;
    Path path = new Path();
    Paint paint = new Paint();
    PlayingField field;
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;


    FieldDrawer(PlayingField field, DisplayMetrics displayMetrics) {
        this.field = field;
        numberOfColumns = field.getNumberOfColumns();
        numberOfRows = field.getNumberOfRows();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public void draw(Canvas canvas) {
        drawField(canvas);
        printScoredPoints(canvas);
    }

    private void drawField(Canvas canvas) {
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                drawCell(i, j, canvas);
            }
        }
    }

    private void printScoredPoints(Canvas canvas) {
        String scoredPoints = String.valueOf(field.getScoredPoints());
        paint.setColor(Color.BLACK);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText(scoredPoints, 50, 50, paint);
    }

    abstract void drawCell(int column, int row, Canvas canvas);

    abstract public void onTouchEvent(float x, float y);
}
