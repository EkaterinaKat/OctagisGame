package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

public abstract class Button {
    private Region fullScreenRegion;
    private Region region = new Region();
    private int currentColor;
    private int color;
    private int pressedButtonColor;
    Path path = new Path();
    int screenWidth;
    int screenHeight;

    Button(int color, int pressedButtonColor, Point displaySize) {
        this.color = color;
        this.pressedButtonColor = pressedButtonColor;
        currentColor = color;
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
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

    void setRegion() {
        region.setPath(path, fullScreenRegion);
    }

    public Path getPath() {
        return path;
    }

    public int getColor() {
        return currentColor;
    }

}
