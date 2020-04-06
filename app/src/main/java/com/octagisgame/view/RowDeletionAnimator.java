package com.octagisgame.view;

import android.graphics.Color;

import com.octagisgame.model.Cell;
import com.octagisgame.model.PlayingField;

import java.util.ArrayList;
import java.util.List;

public class RowDeletionAnimator {
    private final int NUMBER_OF_CYCLES = 4;
    private final int TIME_INTERVAL = 80;
    private PlayingField field;
    private List<Cell> cells;
    private List<Integer> colors;

    public RowDeletionAnimator(PlayingField field) {
        this.field = field;
    }

    public void animateRowDeletion(List<Integer> rows) {
        collectCells(rows);
        saveColors();
        animate();
    }

    private void collectCells(List<Integer> rows) {
        cells = new ArrayList<>();
        for (Integer row : rows) {
            cells.addAll(field.getCellsFromRow(row));
        }
    }

    private void saveColors() {
        colors = new ArrayList<>();
        for (Cell cell : cells) {
            colors.add(cell.getColor());
        }
    }

    private void animate() {
        for (int i = 0; i < NUMBER_OF_CYCLES; i++) {
            setSupplementaryColor();
            sleep();
            setPrimaryColor();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(TIME_INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setSupplementaryColor() {
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).changeColor(Color.WHITE);
        }
    }

    private void setPrimaryColor() {
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).changeColor(colors.get(i));
        }
    }
}
