package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Region;
import android.view.MotionEvent;

import com.octagisgame.activities.GameActivity;

public class PauseButton extends Button {
    /* drawablePath задаёт контур используемый для отрисовки кнопки */
    private Path drawablePath;
    private GameActivity activity;

    PauseButton(GameActivity activity, Region region, Path drawablePath, int color, int pressedButtonColor) {
        super(null, region, color, pressedButtonColor);
        this.drawablePath = drawablePath;
        this.activity = activity;
    }

    @Override
    public Path getPath() {
        return drawablePath;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (pressed(x, y)) {
            visualizePress();
            activity.onPausePressed();
        }
        return false;
    }
}
