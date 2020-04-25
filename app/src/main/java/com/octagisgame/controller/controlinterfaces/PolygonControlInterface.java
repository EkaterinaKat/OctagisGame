package com.octagisgame.controller.controlinterfaces;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

import com.octagisgame.activities.GameActivity;
import com.octagisgame.controller.Game;
import com.octagisgame.controller.buttons.Button;
import com.octagisgame.controller.buttons.ButtonsCreator;
import com.octagisgame.controller.buttons.PauseButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonControlInterface extends ControlInterface {
    private Button leftButton;
    private Button rightButton;
    private Button accelerationButton;
    private Button rotationButton;
    private PauseButton pauseButton;
    private List<Button> controlButtons = new ArrayList<>();
    private GameActivity activity;

    public PolygonControlInterface(GameActivity activity, Point displaySize) {
        this.activity = activity;
        createButtons(activity, displaySize);
    }

    private void createButtons(Context context, Point displaySize) {
        ButtonsCreator buttonsCreator = new ButtonsCreator(displaySize, context);
        leftButton = buttonsCreator.createLeftButton();
        rightButton = buttonsCreator.createRightButton();
        accelerationButton = buttonsCreator.createAccelerationButton();
        rotationButton = buttonsCreator.createRotationButton();
        pauseButton = buttonsCreator.createPauseButton();
        controlButtons.addAll(Arrays.asList(leftButton, rightButton, accelerationButton, rotationButton));
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
        if (accelerationButton.pressed(x, y)) {
            Game.getInstance().accelerateFalling();
            accelerationButton.visualizePress();
        }
        if (pauseButton.pressed(x, y)) {
            activity.onPausePressed();
            pauseButton.visualizePress();
        }
    }

    public List<Button> getControlButtons() {
        return controlButtons;
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }
}
