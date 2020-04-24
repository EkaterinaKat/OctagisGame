package com.octagisgame.controller.controlinterfaces;

import android.content.Context;
import android.graphics.Point;

import com.octagisgame.activities.GameActivity;
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
    private GameActivity activity;

    public PolygonControlInterface(GameActivity activity, Game game, Point displaySize) {
        super(game);
        this.activity = activity;
        createButtons(activity, displaySize);
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
        if (leftButton.pressed(x, y)) {
            game.moveFigureLeft();
            leftButton.visualizePress();
        }
        if (rightButton.pressed(x, y)) {
            game.moveFigureRight();
            rightButton.visualizePress();
        }
        if (rotationButton.pressed(x, y)) {
            game.rotateFigure();
            rotationButton.visualizePress();
        }
        if (speedUpButton.pressed(x, y)) {
            game.speedUpFalling();
            speedUpButton.visualizePress();
        }
        if (pauseButton.pressed(x, y)) {
            activity.onPausePressed();
            pauseButton.visualizePress();
        }
    }

    public List<ControlButton> getControlButtons() {
        return controlButtons;
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }
}
