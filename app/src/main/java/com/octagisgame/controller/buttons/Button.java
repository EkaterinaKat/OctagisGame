package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Region;
import android.view.MotionEvent;

import com.octagisgame.controller.Game;

public class Button {
    /* region задаёт область кнопки, при нажатии на которую происходит событие */
    private Region region;
    private int currentColor;
    private int color;
    private int pressedButtonColor;
    private Game.Command command;

    Button(Game.Command command, Region region, int color, int pressedButtonColor) {
        this.command = command;
        this.region = region;
        this.color = color;
        this.pressedButtonColor = pressedButtonColor;
        currentColor = color;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (pressed(x, y)) {
            visualizePress();
            Game.getInstance().passCommand(command);
        }
        return false;
    }

    void visualizePress() {
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

    boolean pressed(int x, int y) {
        return region.contains(x, y);
    }

    public Path getPath() {
        return region.getBoundaryPath();
    }

    public int getColor() {
        return currentColor;
    }

}
