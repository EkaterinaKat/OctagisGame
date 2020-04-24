package com.octagisgame.controller.controlinterfaces;

import android.view.MotionEvent;

import com.octagisgame.controller.Game;

public abstract class ControlInterface {
    protected Game game;

    ControlInterface(Game game) {
        this.game = game;
    }

    public abstract void onTouchEvent(MotionEvent event);
}
