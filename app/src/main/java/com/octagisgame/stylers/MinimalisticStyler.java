package com.octagisgame.stylers;

import android.graphics.Paint;

public class MinimalisticStyler implements Styler {
    @Override
    public void tunePaintForCell(Paint paint, int cellColour) {
        paint.setColor(cellColour);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void tunePaintForCellBorders(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
    }

    @Override
    public void tunePaintForButtonBorders(Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(0);
    }
}
