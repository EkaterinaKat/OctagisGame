package com.octagisgame.model;

import android.graphics.Color;

import java.util.Date;
import java.util.Random;

class FigureCreator {

    private FigureCreator() {

    }

    private static final boolean[][] I_SHAPE = {
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},};

    private static final boolean[][] O_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}};

    private static final boolean[][] T_SHAPE = {
            {false, false, false, false},
            {true, true, true, false},
            {false, true, false, false},
            {false, false, false, false}};

    private static final boolean[][] L_SHAPE = {
            {false, false, false, false},
            {false, false, false, true},
            {false, true, true, true},
            {false, false, false, false}};

    private static final boolean[][] J_SHAPE = {
            {false, false, false, false},
            {true, false, false, false},
            {true, true, true, false},
            {false, false, false, false}};

    private static final boolean[][] S_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {true, true, false, false},
            {false, false, false, false}};

    private static final boolean[][] Z_SHAPE = {
            {false, false, false, false},
            {false, true, true, false},
            {false, false, true, true},
            {false, false, false, false}};

    private static int RED = Color.rgb(255, 0, 0);
    private static int ORANGE = Color.rgb(255, 100, 0);
    private static int YELLOW = Color.rgb(255, 255, 0);
    private static int GREEN = Color.rgb(0, 128, 0);
    private static int BLUE = Color.rgb(0, 191, 255);
    private static int INDIGO = Color.rgb(0, 0, 128);
    private static int VIOLET = Color.rgb(75, 0, 130);

    private static boolean[][][] shapes = {I_SHAPE, O_SHAPE, T_SHAPE, L_SHAPE, J_SHAPE, S_SHAPE, Z_SHAPE};
    private static int[] colors = {RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET};

    private static Random random = new Random((new Date()).getTime());

    static Figure getRandomFigure() {
        int color = colors[random.nextInt(colors.length)];
        boolean[][] shape = shapes[random.nextInt(shapes.length)];
        return new Figure(shape, color);
    }
}
