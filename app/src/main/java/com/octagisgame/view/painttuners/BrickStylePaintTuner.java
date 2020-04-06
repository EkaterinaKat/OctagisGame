package com.octagisgame.view.painttuners;

import android.graphics.Color;

public class BrickStylePaintTuner extends PaintTuner {
    private final int CELL_BORDER_WIDTH = 6;
    private final int CONTROL_BUTTON_BORDER_WIDTH = 40;
    private final int BORDER_COLOR = Color.WHITE;

    @Override
    void applyParameters() {
        cellBorderWidth = CELL_BORDER_WIDTH;
        borderColor = BORDER_COLOR;
        controlButtonBorderWidth = CONTROL_BUTTON_BORDER_WIDTH;
    }

}
