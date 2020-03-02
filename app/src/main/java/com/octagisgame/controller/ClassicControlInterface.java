package com.octagisgame.controller;

import android.graphics.Point;

public class ClassicControlInterface extends ControlInterface {

    private int screenWidth;

    public ClassicControlInterface(Game game, Point displaySize) {
        super(game);
        screenWidth = displaySize.x;
    }

    @Override
    public void onTouchEvent(int x, int y) {
        if (x < screenWidth / 2)
            game.moveFigureLeft();
        if (x > screenWidth / 2)
            game.moveFigureRight();
    }
}
