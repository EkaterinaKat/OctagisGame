package com.octagisgame.FieldDrawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

abstract public class FieldDrawer {
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

    public void drawField(Canvas canvas) {
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                drawCell(i,j,canvas);
            }
        }
    }

    abstract void drawCell(int column, int row, Canvas canvas);
}
