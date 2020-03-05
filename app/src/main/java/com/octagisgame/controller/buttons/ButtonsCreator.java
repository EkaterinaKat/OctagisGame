package com.octagisgame.controller.buttons;

import android.content.Context;
import android.graphics.Point;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;

public class ButtonsCreator {
    /* Число на, которое умножается высота экрана, для получения высоты кнопок управления */
    private final double BUTTONS_HEIGHT_TO_SCREEN_HEIGHT = 0.35;

    private Context context;
    private Point displaySize;

    private int screenWidth;
    private int screenHeight;
    private int controlButtonsHeight;

    private Point leftTop;
    private Point leftBottom;
    private Point center;
    private Point rightTop;
    private Point rightBottom;

    private int leftAndRightButtonsColor;
    private int speedUpButtonColor;
    private int rotationButtonColor;
    private int pauseButtonColor;

    public ButtonsCreator(Point displaySize, Context context) {
        this.context = context;
        this.displaySize = displaySize;
        setSizes();
        initializeReferencePoints();
        initializeColors();
    }

    private void setSizes(){
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        controlButtonsHeight = (int) (screenHeight * BUTTONS_HEIGHT_TO_SCREEN_HEIGHT);
    }

    private void initializeReferencePoints(){
        leftTop = new Point(0, screenHeight - controlButtonsHeight);
        leftBottom = new Point(0, screenHeight);
        center = new Point(screenWidth / 2, screenHeight - controlButtonsHeight / 2);
        rightTop = new Point(screenWidth, screenHeight - controlButtonsHeight);
        rightBottom = new Point(screenWidth, screenHeight);
    }

    private void initializeColors(){
        leftAndRightButtonsColor = ContextCompat.getColor(context, R.color.leftAndRightButtonsColor);
        speedUpButtonColor = ContextCompat.getColor(context, R.color.speedUpButtonColor);
        rotationButtonColor = ContextCompat.getColor(context, R.color.rotationButtonColor);
        pauseButtonColor = ContextCompat.getColor(context, R.color.pauseButtonColor);
    }

    public ControlButton createLeftButton() {
        return new ControlButton(leftAndRightButtonsColor, displaySize, center, leftBottom, leftTop);
    }

    public ControlButton createRightButton() {
        return new ControlButton(leftAndRightButtonsColor, displaySize, center, rightBottom, rightTop);
    }

    public ControlButton createSpeedUpButton() {
        return new ControlButton(speedUpButtonColor, displaySize, center, leftBottom, rightBottom);
    }

    public ControlButton createRotationButton() {
        return new ControlButton(rotationButtonColor, displaySize, center, leftTop, rightTop);
    }

    public PauseButton createPauseButton() {
        return new PauseButton(pauseButtonColor, displaySize);
    }
}
