package com.octagisgame.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    /* Указывает сколько процентов от высоты поля занимает высота кнопок управления */
    private final double CONTROL_BUTTONS_HEIGHT_PERCENT = 0.35;
    private int screenWidth;
    private int screenHeight;
    private int controlButtonsHeight;
    private ControlButton leftButton;
    private ControlButton rightButton;
    private ControlButton speedUpButton;
    private ControlButton rotationButton;
    private Region fullScreenRegion;
    private List<ControlButton> buttons = new ArrayList<>();
    private Context context;

    public PolygonControlInterface(Context context, Game game, Point displaySize) {
        super(game);
        this.context = context;
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
        controlButtonsHeight = (int) (screenHeight * CONTROL_BUTTONS_HEIGHT_PERCENT);
        createButtons();
    }

    private void createButtons() {
        Point leftTop = new Point(0, screenHeight - controlButtonsHeight);
        Point leftBottom = new Point(0, screenHeight);
        Point center = new Point(screenWidth / 2, screenHeight - controlButtonsHeight / 2);
        Point rightTop = new Point(screenWidth, screenHeight - controlButtonsHeight);
        Point rightBottom = new Point(screenWidth, screenHeight);

        int leftAndRightButtonsColour = ContextCompat.getColor(context, R.color.leftAndRightButtonsColour);
        int speedUpButtonColour = ContextCompat.getColor(context, R.color.speedUpButtonColour);
        int rotationButtonColour = ContextCompat.getColor(context, R.color.rotationButtonColour);

        leftButton = new ControlButton(leftAndRightButtonsColour, center, leftBottom, leftTop);
        rightButton = new ControlButton(leftAndRightButtonsColour, center, rightBottom, rightTop);
        speedUpButton = new ControlButton(speedUpButtonColour, center, leftBottom, rightBottom);
        rotationButton = new ControlButton(rotationButtonColour, center, leftTop, rightTop);
        buttons.addAll(Arrays.asList(leftButton, rightButton, speedUpButton, rotationButton));
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
    }

    public class ControlButton {
        private Path path = new Path();
        private Paint paint = new Paint();
        private Region region = new Region();
        private int color;

        ControlButton(int color, Point... tops) {
            this.color = color;
            setPath(tops);
        }

        private void setPath(Point[] tops) {
            path.moveTo(tops[0].x, tops[0].y);
            for (int i = 1; i < tops.length; i++) {
                path.lineTo(tops[i].x, tops[i].y);
            }
            path.close();
            region.setPath(path, fullScreenRegion);
        }

        public void draw(Canvas canvas) {
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }

        boolean pressed(int x, int y) {
            return region.contains(x, y);
        }
    }

    public List<ControlButton> getButtons() {
        return buttons;
    }
}
