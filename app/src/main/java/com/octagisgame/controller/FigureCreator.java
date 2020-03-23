package com.octagisgame.controller;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;
import com.octagisgame.model.Figure;
import com.octagisgame.model.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FigureCreator {
    private List<Figure> figures;
    private Random random;
    private Context context;

    final Shape I_SHAPE = new Shape(new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false}});

    private final Shape O_SHAPE = new Shape(new boolean[][]{
            {true, true},
            {true, true}});

    private final Shape T_SHAPE = new Shape(new boolean[][]{
            {true, true, true},
            {false, true, false}});

    private final Shape L_SHAPE = new Shape(new boolean[][]{
            {true, false},
            {true, false},
            {true, true}});

    private final Shape J_SHAPE = new Shape(new boolean[][]{
            {false, true},
            {false, true},
            {true, true}});

    private final Shape S_SHAPE = new Shape(new boolean[][]{
            {false, true, true},
            {true, true, false}});

    private final Shape Z_SHAPE = new Shape(new boolean[][]{
            {true, true, false},
            {false, true, true}});

    FigureCreator(Context context) {
        this.context = context;
        initializeFigures();
        random = new Random();
    }

    private void initializeFigures() {
        figures = new ArrayList<>();
        figures.add(new Figure(I_SHAPE, getColour(R.color.blue)));
        figures.add(new Figure(O_SHAPE, getColour(R.color.yellow)));
        figures.add(new Figure(T_SHAPE, getColour(R.color.violet)));
        figures.add(new Figure(L_SHAPE, getColour(R.color.orange)));
        figures.add(new Figure(J_SHAPE, getColour(R.color.indigo)));
        figures.add(new Figure(S_SHAPE, getColour(R.color.green)));
        figures.add(new Figure(Z_SHAPE, getColour(R.color.red)));
    }

    private int getColour(int resource) {
        return ContextCompat.getColor(context, resource);
    }

    Figure getRandomFigure() {
        int index = random.nextInt(figures.size());
        return figures.get(index).copy();
    }
}
