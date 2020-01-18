package com.octagisgame.model;

import android.graphics.Color;

public class Cell {
    private boolean filled;
    private int color;

    public Cell() {
        makeEmpty();
    }

    public boolean isFilled() {
        return filled;
    }

    public int getColor() {
        return color;
    }

    public void makeFilled(int color) {
        filled=true;
        this.color = color;
    }

    public void makeEmpty(){
        filled = false;
        color = Color.WHITE;
    }
}
