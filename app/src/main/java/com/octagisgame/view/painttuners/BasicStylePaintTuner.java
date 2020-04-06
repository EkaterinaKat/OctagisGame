package com.octagisgame.view.painttuners;

import android.graphics.Color;

public class BasicStylePaintTuner extends PaintTuner {
    private final int BORDER_WIDTH = 1;
    private final int BORDER_COLOR = Color.BLACK;

    @Override
    void applyParameters() {
        cellBorderWidth = BORDER_WIDTH;
        borderColor = BORDER_COLOR;
        controlButtonBorderWidth = BORDER_WIDTH;
    }
}
