package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Point;

public class PauseButton extends Button {
    /* Число, на которое умножается ширина экрана, для получения радиуса кнопки паузы */
    private final double RADIUS_TO_HEIGHT = 0.09;
    /* Число, на которое умножается ширина экрана, для получения горизонтальной координаты
     * центра кнопки паузы */
    private final double BUTTON_X_TO_SCREEN_WIDTH = 0.8;
    /* Число, на которое умножается высота экрана, для получения вертикальной координаты
     * центра кнопки паузы */
    private final double BUTTON_Y_TO_SCREEN_HEIGHT = 0.5;

    private int radius;
    private int centerX;
    private int centerY;

    PauseButton(int color, Point displaySize) {
        super(color, displaySize);
        radius = (int) (screenWidth * RADIUS_TO_HEIGHT);
        centerX = (int) (screenWidth * BUTTON_X_TO_SCREEN_WIDTH);
        centerY = (int) (screenHeight * BUTTON_Y_TO_SCREEN_HEIGHT);
        path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
        setRegion();
        addPauseSymbolOnButton();
    }

    private void addPauseSymbolOnButton(){
        path.addRect(centerX-radius/3, centerY-radius/2, centerX-radius/6,
                centerY+radius/2, Path.Direction.CCW);
        path.addRect(centerX+radius/6, centerY-radius/2, centerX+radius/3,
                centerY+radius/2, Path.Direction.CCW);
    }
}
