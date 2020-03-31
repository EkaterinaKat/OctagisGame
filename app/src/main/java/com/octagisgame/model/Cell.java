package com.octagisgame.model;

import android.graphics.Path;

public class Cell {
    private boolean filled;
    private int color;
    private Path outline;

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
    }

    public void changeColor(int color) {
        this.color = color;
    }

    public Path getOutline() {
        return outline;
    }

    public void setOutline(Path outline) {
        this.outline = outline;
    }
}
