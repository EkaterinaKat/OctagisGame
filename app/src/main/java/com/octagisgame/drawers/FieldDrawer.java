package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.octagisgame.model.PlayingField;
import com.octagisgame.stylers.Styler;

abstract public class FieldDrawer {
    Path path;
    Paint paint;
    PlayingField field;
    int numberOfColumns;
    int numberOfRows;
    int screenWidth;
    int screenHeight;
    Styler styler;

    FieldDrawer(PlayingField field, Point displaySize, Styler styler) {
        this.styler = styler;
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.field = field;
        numberOfColumns = field.getNumberOfColumns();
        numberOfRows = field.getNumberOfRows();
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawField(canvas);
        drawInterface(canvas);
    }

    private void drawField(Canvas canvas) {
        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {
                drawCell(column, row, canvas);
            }
        }
    }

    abstract void drawCell(int column, int row, Canvas canvas);

    abstract void drawInterface(Canvas canvas); //todo хмммммм
}
