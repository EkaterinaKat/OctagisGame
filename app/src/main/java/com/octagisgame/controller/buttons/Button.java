package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Region;

public class Button {
    /* region задаёт область кнопки, при нажатии на которую происходит событие */
    private Region region;
    private int currentColor;
    private int color;
    private int pressedButtonColor;

    Button(Region region, int color, int pressedButtonColor) {
        this.region = region;
        this.color = color;
        this.pressedButtonColor = pressedButtonColor;
        currentColor = color;
    }

    public void visualizePress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                currentColor = pressedButtonColor;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentColor = color;
            }
        }).start();
    }

    public boolean pressed(int x, int y) {
        return region.contains(x, y);
    }

    public Path getPath() {
        return region.getBoundaryPath();
    }

    public int getColor() {
        return currentColor;
    }

}
