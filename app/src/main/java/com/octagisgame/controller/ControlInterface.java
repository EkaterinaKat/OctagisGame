package com.octagisgame.controller;

public abstract class ControlInterface {
    protected Game game;

    public ControlInterface(Game game) {
        this.game = game;
    }

    public abstract void onTouchEvent(int x, int y);
}
