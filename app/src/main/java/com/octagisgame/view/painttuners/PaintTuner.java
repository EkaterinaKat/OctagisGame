package com.octagisgame.view.painttuners;

import android.graphics.Color;
import android.graphics.Paint;

abstract public class PaintTuner {
    private final int TEXT_SIZE = 40;
    private final int TEXT_COLOR = Color.BLACK;
    private final int PAUSE_BUTTON_OUTLINE_WIDTH = 10;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int cellBorderWidth;
    int borderColor;
    int controlButtonBorderWidth;

    PaintTuner() {
        applyParameters();
    }

    abstract void applyParameters();

    public Paint getTextPaint() {
        paint.setColor(TEXT_COLOR);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TEXT_SIZE);
        return paint;
    }

    public Paint getCellPaint(int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public Paint getCellBorderPaint() {
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(cellBorderWidth);
        return paint;
    }

    public Paint getControlButtonPaint(int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public Paint getControlButtonBorderPaint() {
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(controlButtonBorderWidth);
        return paint;
    }

    public Paint getPauseButtonPaint(int color) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(PAUSE_BUTTON_OUTLINE_WIDTH);
        paint.setColor(color);
        return paint;
    }
}
