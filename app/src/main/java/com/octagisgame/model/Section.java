package com.octagisgame.model;

class Section {

    private int column;
    private int row;

    Section(int column, int row) {
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
