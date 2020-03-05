package com.octagisgame.controller;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonControlInterface extends ControlInterface {
    /* Число на, которое умножается высота экрана, для получения высоты кнопок управления */
    private final double CONTROL_BUTTONS_HEIGHT_PERCENT = 0.35;
    private int screenWidth;
    private int screenHeight;
    private int controlButtonsHeight;
    private ControlButton leftButton;
    private ControlButton rightButton;
    private ControlButton speedUpButton;
    private ControlButton rotationButton;
    private PauseButton pauseButton;
    private Region fullScreenRegion;
    private List<ControlButton> controlButtons = new ArrayList<>();
    private Context context;

    public PolygonControlInterface(Context context, Game game, Point displaySize) {
        super(game);
        this.context = context;
        setSizes(displaySize);
        createButtons();
    }

    private void setSizes(Point displaySize) {
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
        controlButtonsHeight = (int) (screenHeight * CONTROL_BUTTONS_HEIGHT_PERCENT);
    }

    private void createButtons() {
        Point leftTop = new Point(0, screenHeight - controlButtonsHeight);
        Point leftBottom = new Point(0, screenHeight);
        Point center = new Point(screenWidth / 2, screenHeight - controlButtonsHeight / 2);
        Point rightTop = new Point(screenWidth, screenHeight - controlButtonsHeight);
        Point rightBottom = new Point(screenWidth, screenHeight);

        int leftAndRightButtonsColor = ContextCompat.getColor(context, R.color.leftAndRightButtonsColor);
        int speedUpButtonColor = ContextCompat.getColor(context, R.color.speedUpButtonColor);
        int rotationButtonColor = ContextCompat.getColor(context, R.color.rotationButtonColor);
        int pauseButtonColor = ContextCompat.getColor(context, R.color.pauseButtonColor);

        leftButton = new ControlButton(leftAndRightButtonsColor, center, leftBottom, leftTop);
        rightButton = new ControlButton(leftAndRightButtonsColor, center, rightBottom, rightTop);
        speedUpButton = new ControlButton(speedUpButtonColor, center, leftBottom, rightBottom);
        rotationButton = new ControlButton(rotationButtonColor, center, leftTop, rightTop);
        controlButtons.addAll(Arrays.asList(leftButton, rightButton, speedUpButton, rotationButton));

        pauseButton = new PauseButton(pauseButtonColor);
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
        if(pauseButton.pressed(x, y))
            game.setOnPause();
    }

    abstract public class Button {
        Region region = new Region();
        Path path = new Path();
        private int color;

        public Button(int color) {
            this.color = color;
        }

        boolean pressed(int x, int y) {
            return region.contains(x, y);
        }

        void setRegion() {
            region.setPath(path, fullScreenRegion);
        }

        public Path getPath() {
            return path;
        }

        public int getColor() {
            return color;
        }
    }

    public class PauseButton extends Button {
        /* Число, на которое умножается ширина экрана, для получения радиуса кнопки паузы */
        private final double RADIUS_TO_HEIGHT = 0.09;
        /* Число, на которое умножается ширина экрана, для получения горизонтальной координаты
         * центра кнопки паузы */
        private final double BUTTON_X_TO_SCREEN_WIDTH = 0.8;
        /* Число, на которое умножается высота экрана, для получения вертикальной координаты
         * центра кнопки паузы */
        private final double BUTTON_Y_TO_SCREEN_HEIGHT = 0.5;

        PauseButton(int color) {
            super(color);
            int radius = (int) (screenWidth * RADIUS_TO_HEIGHT);
            int centerX = (int) (screenWidth * BUTTON_X_TO_SCREEN_WIDTH);
            int centerY = (int) (screenHeight * BUTTON_Y_TO_SCREEN_HEIGHT);
            path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
            setRegion();
        }
    }

    public class ControlButton extends Button {
        ControlButton(int color, Point... tops) {
            super(color);
            setPath(tops);
            setRegion();
        }

        void setPath(Point[] tops) {
            path.moveTo(tops[0].x, tops[0].y);
            for (int i = 1; i < tops.length; i++) {
                path.lineTo(tops[i].x, tops[i].y);
            }
            path.close();
        }
    }

    public List<ControlButton> getControlButtons() {
        return controlButtons;
    }

    public PauseButton getPauseButton() {
        return pauseButton;
    }
}
