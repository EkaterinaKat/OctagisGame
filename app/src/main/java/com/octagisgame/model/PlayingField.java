package com.octagisgame.model;

public class PlayingField {
    private final int NUMBER_OF_COLUMNS = 8;
    private final int NUMBER_OF_ROWS = 12;
    private Cell[][] cells = new Cell[NUMBER_OF_COLUMNS][NUMBER_OF_ROWS];

    public Cell getCell(int column, int row){
        return cells[column][row];
    }

}
