package com.octagisgame.model;

import android.graphics.Color;

public class Cell {
    private boolean filled;
    private int color;

    Cell() {
        makeEmpty();
    }

    boolean isFilled() {
        return filled;
    }

    public int getColor() {
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

    public void changeColor(int color) {
        this.color = color;
    }
}
