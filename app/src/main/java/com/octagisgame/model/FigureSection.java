package com.octagisgame.model;

class FigureSection {

    private int column;
    private int row;

    FigureSection(int column, int row) {
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
