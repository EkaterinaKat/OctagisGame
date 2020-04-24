package com.octagisgame.controller.controlinterfaces;

import android.graphics.Point;
import android.view.MotionEvent;

import com.octagisgame.controller.Game;

public class ClassicControlInterface extends ControlInterface {

    private int screenWidth;

    public ClassicControlInterface(Game game, Point displaySize) {
        super(game);
        screenWidth = displaySize.x;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        float x = event.getX();
        if (x < screenWidth / 2)
            game.moveFigureLeft();
        if (x > screenWidth / 2)
            game.moveFigureRight();
    }
}
