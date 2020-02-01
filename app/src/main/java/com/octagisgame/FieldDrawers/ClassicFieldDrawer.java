package com.octagisgame.FieldDrawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

public class ClassicFieldDrawer extends FieldDrawer {
    private int cellHeight;
    private int cellWidth;
    private Point startingPoint; //верхняя левая точка поля

    public ClassicFieldDrawer(PlayingField field, DisplayMetrics displayMetrics) {
        super(field, displayMetrics);
        cellHeight = screenHeight / numberOfRows;
        cellWidth = screenWidth / (2 * numberOfColumns);
        startingPoint = new Point(screenWidth / 4, 0);
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
}
