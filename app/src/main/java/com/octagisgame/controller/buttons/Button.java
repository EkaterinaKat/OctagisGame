package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

public abstract class Button {
    private Region fullScreenRegion;
    private Region region = new Region();
    private int color;
    Path path = new Path();
    int screenWidth;
    int screenHeight;

    Button(int color, Point displaySize) {
        this.color = color;
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
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
        return color;
    }

}
