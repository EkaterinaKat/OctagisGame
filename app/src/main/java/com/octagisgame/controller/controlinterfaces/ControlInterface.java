package com.octagisgame.controller.controlinterfaces;

import com.octagisgame.controller.Game;

public abstract class ControlInterface {
    protected Game game;

    ControlInterface(Game game) {
        this.game = game;
    }

    public abstract void onTouchEvent(int x, int y);
}
