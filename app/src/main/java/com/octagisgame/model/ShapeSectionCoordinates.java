package com.octagisgame.model;

class ShapeSectionCoordinates {

    private int column;
    private int row;

    ShapeSectionCoordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }
}
