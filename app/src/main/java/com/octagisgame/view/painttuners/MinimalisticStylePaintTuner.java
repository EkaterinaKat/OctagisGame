package com.octagisgame.view.painttuners;

public class MinimalisticStylePaintTuner extends PaintTuner {
    private final int BORDER_WIDTH = 0;
    private final int BORDER_COLOR = 0;

    @Override
    void applyParameters() {
        cellBorderWidth = BORDER_WIDTH;
        borderColor = BORDER_COLOR;
        controlButtonBorderWidth = BORDER_WIDTH;
    }

}
