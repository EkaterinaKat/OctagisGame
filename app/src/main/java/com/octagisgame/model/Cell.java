package com.octagisgame.model;

import android.graphics.Color;

class Cell {
    private boolean filled;
    private int color;

    Cell() {
        makeEmpty();
    }

    boolean isFilled() {
        return filled;
    }

    int getColor() {
        return color;
    }

    void makeFilled(int color) {
        filled = true;
        this.color = color;
    }

    private void makeEmpty() {
        filled = false;
        color = Color.WHITE;
    }
}
