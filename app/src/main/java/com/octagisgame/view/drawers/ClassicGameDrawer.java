package com.octagisgame.view.drawers;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.octagisgame.controller.Game;
import com.octagisgame.view.painttuners.PaintTuner;

public class ClassicGameDrawer extends GameDrawer {
    private int cellHeight;
    private int cellWidth;
    /* Верхняя левая точка поля */
    private Point startingPoint;

    public ClassicGameDrawer(Game game, Point displaySize, PaintTuner paintTuner) {
        super(game, displaySize, paintTuner);
        setSizes();
    }

    private void setSizes() {
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
        canvas.drawRect(rect, paintTuner.getCellPaint(cellColour));
        canvas.drawRect(rect, paintTuner.getCellBorderPaint());
    }
}
