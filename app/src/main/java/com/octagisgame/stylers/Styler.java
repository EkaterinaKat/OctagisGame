package com.octagisgame.stylers;

import android.graphics.Paint;

public interface Styler {
    void tunePaintForCell(Paint paint, int cellColour);
    void tunePaintForCellBorders(Paint paint);
    void tunePaintForButtonBorders(Paint paint);
}
