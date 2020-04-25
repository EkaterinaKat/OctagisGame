package com.octagisgame.controller.buttons;

import android.graphics.Path;
import android.graphics.Region;

public class PauseButton extends Button {
    /* drawablePath задаёт контур используемый для отрисовки кнопки */
    private Path drawablePath;

    PauseButton(Region region, Path drawablePath, int color, int pressedButtonColor) {
        super(region, color, pressedButtonColor);
        this.drawablePath = drawablePath;
    }

    @Override
    public Path getPath() {
        return drawablePath;
    }
}
