package com.octagisgame.controller.buttons;

import android.graphics.Point;

public class ControlButton extends Button {

    ControlButton(int color, Point displaySize, Point... tops) {
        super(color, displaySize);
        setPath(tops);
        setRegion();
    }

    private void setPath(Point[] tops) {
        path.moveTo(tops[0].x, tops[0].y);
        for (int i = 1; i < tops.length; i++) {
            path.lineTo(tops[i].x, tops[i].y);
        }
        path.close();
    }
}
