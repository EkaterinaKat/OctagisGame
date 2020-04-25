package com.octagisgame.controller.controlinterfaces;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

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

    public PolygonControlInterface(GameActivity activity, Point displaySize) {
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
    public void onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (leftButton.pressed(x, y)) {
            Game.getInstance().moveFigureLeft();
            leftButton.visualizePress();
        }
        if (rightButton.pressed(x, y)) {
            Game.getInstance().moveFigureRight();
            rightButton.visualizePress();
        }
        if (rotationButton.pressed(x, y)) {
            Game.getInstance().rotateFigure();
            rotationButton.visualizePress();
        }
        if (speedUpButton.pressed(x, y)) {
            Game.getInstance().speedUpFalling();
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
