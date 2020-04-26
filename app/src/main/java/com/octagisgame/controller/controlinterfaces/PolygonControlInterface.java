package com.octagisgame.controller.controlinterfaces;

import android.graphics.Point;
import android.view.MotionEvent;

import com.octagisgame.activities.GameActivity;
import com.octagisgame.controller.buttons.Button;
import com.octagisgame.controller.buttons.ButtonsCreator;
import com.octagisgame.controller.buttons.PauseButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonControlInterface extends ControlInterface {
    private PauseButton pauseButton;
    private List<Button> buttons = new ArrayList<>();
    private List<Button> controlButtons = new ArrayList<>();
    private GameActivity activity;

    public PolygonControlInterface(GameActivity activity, Point displaySize) {
        this.activity = activity;
        createButtons(activity, displaySize);
    }

    private void createButtons(GameActivity activity, Point displaySize) {
        ButtonsCreator buttonsCreator = new ButtonsCreator(displaySize, activity);
        Button leftButton = buttonsCreator.createLeftButton();
        Button rightButton = buttonsCreator.createRightButton();
        Button accelerationButton = buttonsCreator.createAccelerationButton();
        Button rotationButton = buttonsCreator.createRotationButton();
        pauseButton = buttonsCreator.createPauseButton();
        controlButtons.addAll(Arrays.asList(leftButton, rightButton, accelerationButton, rotationButton));
        buttons.addAll(controlButtons);
        buttons.add(pauseButton);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        for (Button button : buttons) {
            button.onTouchEvent(event);
        }
    }

    public List<Button> getControlButtons() {
        return controlButtons;
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }
}
