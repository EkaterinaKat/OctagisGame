package com.octagisgame.controller.controlinterfaces;

import android.content.Context;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.controller.buttons.ButtonsCreator;
import com.octagisgame.controller.buttons.ControlButton;
import com.octagisgame.controller.buttons.PauseButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonControlInterface extends ControlInterface {
    private ControlButton leftButton;
    private ControlButton rightButton;
    private ControlButton speedUpButton;
    private ControlButton rotationButton;
    private PauseButton pauseButton;
    private List<ControlButton> controlButtons = new ArrayList<>();

    public PolygonControlInterface(Context context, Game game, Point displaySize) {
        super(game);
        createButtons(context, displaySize);
    }

    private void createButtons(Context context, Point displaySize) {
        ButtonsCreator buttonsCreator = new ButtonsCreator(displaySize, context);
        leftButton = buttonsCreator.createLeftButton();
        rightButton = buttonsCreator.createRightButton();
        speedUpButton = buttonsCreator.createSpeedUpButton();
        rotationButton = buttonsCreator.createRotationButton();
        pauseButton = buttonsCreator.createPauseButton();
        controlButtons.addAll(Arrays.asList(leftButton, rightButton, speedUpButton, rotationButton));
    }

    @Override
    public void onTouchEvent(int x, int y) {
        if (leftButton.pressed(x, y))
            game.moveFigureLeft();
        if (rightButton.pressed(x, y))
            game.moveFigureRight();
        if (rotationButton.pressed(x, y))
            game.rotateFigure();
        if (speedUpButton.pressed(x, y))
            game.speedUpFalling();
        if (pauseButton.pressed(x, y))
            game.setOnPause();
    }

    public List<ControlButton> getControlButtons() {
        return controlButtons;
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }
}
