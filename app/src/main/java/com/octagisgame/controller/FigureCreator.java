package com.octagisgame.controller;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;
import com.octagisgame.model.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.octagisgame.model.ShapeTypes.I_SHAPE;
import static com.octagisgame.model.ShapeTypes.J_SHAPE;
import static com.octagisgame.model.ShapeTypes.L_SHAPE;
import static com.octagisgame.model.ShapeTypes.O_SHAPE;
import static com.octagisgame.model.ShapeTypes.S_SHAPE;
import static com.octagisgame.model.ShapeTypes.T_SHAPE;
import static com.octagisgame.model.ShapeTypes.Z_SHAPE;

class FigureCreator {
    private List<Figure> figures;
    private Random random;
    private Context context;

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
