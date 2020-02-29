package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.octagisgame.controller.Game;
import com.octagisgame.stylers.Styler;

public class ClassicFieldDrawer extends FieldDrawer {
    private int cellHeight;
    private int cellWidth;
    /* Верхняя левая точка поля */
    private Point startingPoint;

    public ClassicFieldDrawer(Game game, Point displaySize, Styler styler) {
        super(game, displaySize, styler);
        int fieldWidth = screenWidth;
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
        int cellColour = game.getCellColour(column, row);
        styler.tunePaintForCell(paint, cellColour);
        canvas.drawRect(rect, paint);
        styler.tunePaintForCellBorders(paint, cellColour);
        canvas.drawRect(rect, paint);
    }
}
