package com.octagisgame.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolygonControlInterface extends ControlInterface{
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

    public PolygonControlInterface(Game game, Point displaySize) {
        super(game);
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
        controlButtonsHeight = (int) (screenHeight * CONTROL_BUTTONS_HEIGHT_PERCENT);
        setInterfaceElementsCoordinates();
    }

    private void setInterfaceElementsCoordinates() {
        Point leftTop = new Point(0, screenHeight - controlButtonsHeight);
        Point leftBottom = new Point(0, screenHeight);
        Point center = new Point(screenWidth / 2, screenHeight - controlButtonsHeight / 2);
        Point rightTop = new Point(screenWidth, screenHeight - controlButtonsHeight);
        Point rightBottom = new Point(screenWidth, screenHeight);
        //todo с этим безобразием что-то сделать
        leftButton = new ControlButton(Color.argb(130, 100, 149, 237), center, leftBottom, leftTop);
        rightButton = new ControlButton(Color.argb(130, 100, 149, 237),center, rightBottom, rightTop);
        speedUpButton = new ControlButton(Color.argb(130, 250, 128, 114),center, leftBottom, rightBottom);
        rotationButton = new ControlButton(Color.argb(130, 173, 255, 47),center, leftTop, rightTop);
        buttons.addAll(Arrays.asList(leftButton, rightButton, speedUpButton, rotationButton));
    }

    @Override
    public void onTouchEvent(int x, int y) {
        if(leftButton.pressed(x,y))
            game.moveFigureLeft();
        if(rightButton.pressed(x,y))
            game.moveFigureRight();
        if(rotationButton.pressed(x,y))
            game.rotateFigure();
        if(speedUpButton.pressed(x,y))
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

        private void setPath(Point[] tops){
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
