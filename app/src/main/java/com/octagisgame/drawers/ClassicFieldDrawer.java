package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

public class ClassicFieldDrawer extends FieldDrawer {
    private final double RELATIVE_FIELD_WIDTH = 0.5;
    private int cellHeight;
    private int cellWidth;
    private int fieldWidth;
    private Point startingPoint; //верхняя левая точка поля

    public ClassicFieldDrawer(PlayingField field, DisplayMetrics displayMetrics) {
        super(field, displayMetrics);
        fieldWidth = (int)(screenWidth*RELATIVE_FIELD_WIDTH);
        cellHeight = screenHeight / numberOfRows;
        cellWidth = fieldWidth / numberOfColumns;
        startingPoint = new Point((screenWidth - fieldWidth) / 2, 0);
    }

    @Override
    void drawCell(int column, int row, Canvas canvas) {
        int top = startingPoint.y + (row * cellHeight);
        int bottom = startingPoint.y + ((row + 1) * cellHeight);
        int left = startingPoint.x + (column * cellWidth);
        int right = startingPoint.x + ((column + 1) * cellWidth);
        Rect rect = new Rect(left, top, right, bottom);
        paint.setColor(field.getCellColour(column, row));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void onTouchEvent(float x, float y) {
        if (x < (screenWidth - fieldWidth) / 2) {
            field.moveFigureLeft();
        } else if (x > (screenWidth + fieldWidth) / 2) {
            field.moveFigureRight();
        } else {
            field.rotateFigure();
        }
    }
}
