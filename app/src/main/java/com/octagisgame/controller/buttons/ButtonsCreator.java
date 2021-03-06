package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

import androidx.core.content.ContextCompat;

import com.octagisgame.R;
import com.octagisgame.activities.GameActivity;
import com.octagisgame.controller.Game;

public class ButtonsCreator {
    /* Число на, которое умножается высота экрана, для получения высоты кнопок управления */
    private final double BUTTONS_HEIGHT_TO_SCREEN_HEIGHT = 0.35;

    private GameActivity activity;
    private Point displaySize;
    private Region fullScreenRegion;

    private int screenWidth;
    private int screenHeight;
    private int controlButtonsHeight;

    private Point leftTop;
    private Point leftBottom;
    private Point center;
    private Point rightTop;
    private Point rightBottom;

    private int leftAndRightButtonsColor;
    private int accelerationButtonColor;
    private int rotationButtonColor;
    private int pauseButtonColor;
    private int leftAndRightPressedButtonsColor;
    private int accelerationPressedButtonColor;
    private int rotationPressedButtonColor;
    private int pausePressedButtonColor;

    public ButtonsCreator(Point displaySize, GameActivity activity) {
        this.activity = activity;
        this.displaySize = displaySize;
        setSizes();
        initializeReferencePoints();
        initializeColors();
    }

    private void setSizes() {
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
        controlButtonsHeight = (int) (screenHeight * BUTTONS_HEIGHT_TO_SCREEN_HEIGHT);
    }

    private void initializeReferencePoints() {
        leftTop = new Point(0, screenHeight - controlButtonsHeight);
        leftBottom = new Point(0, screenHeight);
        center = new Point(screenWidth / 2, screenHeight - controlButtonsHeight / 2);
        rightTop = new Point(screenWidth, screenHeight - controlButtonsHeight);
        rightBottom = new Point(screenWidth, screenHeight);
    }

    private void initializeColors() {
        leftAndRightButtonsColor = ContextCompat.getColor(activity, R.color.leftAndRightButtonsColor);
        accelerationButtonColor = ContextCompat.getColor(activity, R.color.accelerationButtonColor);
        rotationButtonColor = ContextCompat.getColor(activity, R.color.rotationButtonColor);
        pauseButtonColor = ContextCompat.getColor(activity, R.color.pauseButtonColor);
        leftAndRightPressedButtonsColor = ContextCompat.getColor(activity, R.color.leftAndRightPressedButtonsColor);
        accelerationPressedButtonColor = ContextCompat.getColor(activity, R.color.accelerationPressedButtonColor);
        rotationPressedButtonColor = ContextCompat.getColor(activity, R.color.rotationPressedButtonColor);
        pausePressedButtonColor = ContextCompat.getColor(activity, R.color.pausePressedButtonColor);
    }

    public Button createLeftButton() {
        Region leftButtonRegion = getRegion(getControlButtonPath(center, leftBottom, leftTop));
        return new Button(Game.Command.LEFT, leftButtonRegion, leftAndRightButtonsColor,
                leftAndRightPressedButtonsColor);
    }

    public Button createRightButton() {
        Region rightButtonRegion = getRegion(getControlButtonPath(center, rightBottom, rightTop));
        return new Button(Game.Command.RIGHT, rightButtonRegion, leftAndRightButtonsColor,
                leftAndRightPressedButtonsColor);
    }

    public Button createAccelerationButton() {
        Region accelerationButtonRegion = getRegion(getControlButtonPath(center, leftBottom, rightBottom));
        return new Button(Game.Command.ACCELERATE, accelerationButtonRegion, accelerationButtonColor,
                accelerationPressedButtonColor);
    }

    public Button createRotationButton() {
        Region rotationButtonRegion = getRegion(getControlButtonPath(center, leftTop, rightTop));
        return new Button(Game.Command.ROTATE, rotationButtonRegion, rotationButtonColor,
                rotationPressedButtonColor);
    }

    public PauseButton createPauseButton() {
        return new PauseButtonCreator().create();
    }

    private Region getRegion(Path path) {
        Region region = new Region();
        region.setPath(path, fullScreenRegion);
        return region;
    }

    private Path getControlButtonPath(Point... tops) {
        Path path = new Path();
        path.moveTo(tops[0].x, tops[0].y);
        for (int i = 1; i < tops.length; i++) {
            path.lineTo(tops[i].x, tops[i].y);
        }
        path.close();
        return path;
    }

    private class PauseButtonCreator {
        /* Число, на которое умножается ширина экрана, для получения радиуса кнопки паузы */
        private final double PAUSE_BUTTON_RADIUS_TO_HEIGHT = 0.09;
        /* Число, на которое умножается ширина экрана, для получения горизонтальной координаты
         * центра кнопки паузы */
        private final double PAUSE_BUTTON_X_TO_SCREEN_WIDTH = 0.8;
        /* Число, на которое умножается высота экрана, для получения вертикальной координаты
         * центра кнопки паузы */
        private final double PAUSE_BUTTON_Y_TO_SCREEN_HEIGHT = 0.5;

        private Path path = new Path();
        private int radius;
        private int centerX;
        private int centerY;

        public PauseButton create() {
            addCircleToPath();
            Region pauseButtonRegion = getRegion(path);
            addPauseSymbolToPath();
            return new PauseButton(activity, pauseButtonRegion, path, pauseButtonColor,
                    pausePressedButtonColor);
        }

        private void addCircleToPath() {
            radius = (int) (screenWidth * PAUSE_BUTTON_RADIUS_TO_HEIGHT);
            centerX = (int) (screenWidth * PAUSE_BUTTON_X_TO_SCREEN_WIDTH);
            centerY = (int) (screenHeight * PAUSE_BUTTON_Y_TO_SCREEN_HEIGHT);
            path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        }

        private void addPauseSymbolToPath() {
            path.addRect(centerX - radius / 3.0f, centerY - radius / 2.0f,
                    centerX - radius / 6.0f, centerY + radius / 2.0f, Path.Direction.CCW);
            path.addRect(centerX + radius / 6.0f, centerY - radius / 2.0f,
                    centerX + radius / 3.0f, centerY + radius / 2.0f, Path.Direction.CCW);
        }
    }
}
