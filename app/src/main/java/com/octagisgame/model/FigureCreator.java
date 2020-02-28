package com.octagisgame.model;

import android.graphics.Color;

import java.util.Date;
import java.util.Random;

public class FigureCreator {
    private int initialHorizontalPosition;

    public FigureCreator(int numberOfColumns) {
        initialHorizontalPosition = numberOfColumns / 2 - 2;
    }

    private final boolean[][] I_SHAPE = {
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},};

    private final boolean[][] O_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}};

    private final boolean[][] T_SHAPE = {
            {false, false, false, false},
            {true, true, true, false},
            {false, true, false, false},
            {false, false, false, false}};

    private final boolean[][] L_SHAPE = {
            {false, false, false, false},
            {false, false, false, true},
            {false, true, true, true},
            {false, false, false, false}};

    private final boolean[][] J_SHAPE = {
            {false, false, false, false},
            {true, false, false, false},
            {true, true, true, false},
            {false, false, false, false}};

    private final boolean[][] S_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {true, true, false, false},
            {false, false, false, false}};

    private final boolean[][] Z_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {false, false, true, true},
            {false, false, false, false}};

    private int RED = Color.rgb(255, 8, 0);
    private int ORANGE = Color.rgb(254, 93, 3);
    private int YELLOW = Color.rgb(250, 215, 0);
    private int GREEN = Color.rgb(75, 254, 1);
    private int BLUE = Color.rgb(0, 214, 220);
    private int INDIGO = Color.rgb(6, 0, 254);
    private int VIOLET = Color.rgb(255, 0, 218);

    private boolean[][][] shapes = {I_SHAPE, O_SHAPE, T_SHAPE, L_SHAPE, J_SHAPE, S_SHAPE, Z_SHAPE};
    private int[] colors = {RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET};

    private Random random = new Random((new Date()).getTime());

    public Figure getRandomFigure() {
        int color = colors[random.nextInt(colors.length)];
        boolean[][] shape = shapes[random.nextInt(shapes.length)];
        return new Figure(shape, color, initialHorizontalPosition);
    }
}
