package com.octagisgame.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FigureCreator {
    private List<Figure> figures;
    private Random random;

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

    public FigureCreator() {
        initializeFigures();
        random = new Random();
    }

    private void initializeFigures() {
        figures = new ArrayList<>();
        figures.add(new Figure(I_SHAPE, BLUE));
        figures.add(new Figure(O_SHAPE, YELLOW));
        figures.add(new Figure(T_SHAPE, VIOLET));
        figures.add(new Figure(L_SHAPE, ORANGE));
        figures.add(new Figure(J_SHAPE, INDIGO));
        figures.add(new Figure(S_SHAPE, GREEN));
        figures.add(new Figure(Z_SHAPE, RED));
    }

    public Figure getRandomFigure() {
        int index = random.nextInt(figures.size());
        return figures.get(index).copy();
    }
}
