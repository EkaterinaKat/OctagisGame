package com.octagisgame.stylers;

import android.graphics.Color;
import android.graphics.Paint;

public class BrickStyler implements Styler {
    @Override
    public void tunePaintForCell(Paint paint, int cellColour) {
        paint.setColor(cellColour);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void tunePaintForCellBorders(Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
    }

    @Override
    public void tunePaintForButtonBorders(Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40);
    }
}
