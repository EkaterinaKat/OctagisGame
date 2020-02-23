package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;

import com.octagisgame.model.PlayingField;

public class PolygonFieldInterfaceDrawer {
    /* Указывает сколько процентов от высоты поля занимает высота кнопок управления */
    private final double CONTROL_BUTTONS_HEIGHT_PERCENT = 0.35;
    private final int TEXT_SIZE = 40;
    private int controlButtonsHeight;
    private PlayingField field;
    private Region fullScreenRegion;
    private Paint paint = new Paint();
    private int screenWidth;
    private int screenHeight;
    private ControlButton leftButton;
    private ControlButton rightButton;
    private ControlButton speedUpButton;
    private ControlButton rotationButton;

    public PolygonFieldInterfaceDrawer(PlayingField field, Point displaySize) {
        this.field = field;
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        controlButtonsHeight = (int) (screenHeight * CONTROL_BUTTONS_HEIGHT_PERCENT);
        fullScreenRegion = new Region(new Rect(0, 0, screenWidth, screenHeight));
        setInterfaceElementsCoordinates();
    }

    private void setInterfaceElementsCoordinates() {
        Point leftTop = new Point(0, screenHeight-controlButtonsHeight);
        Point leftBottom = new Point(0, screenHeight);
        Point center = new Point(screenWidth/2, screenHeight-controlButtonsHeight/2);
        Point rightTop = new Point(screenWidth, screenHeight-controlButtonsHeight);
        Point rightBottom = new Point(screenWidth, screenHeight);
        leftButton = new ControlButton(center, leftBottom, leftTop);
        rightButton = new ControlButton(center, rightBottom, rightTop);
        speedUpButton = new ControlButton(center,leftBottom,rightBottom);
        rotationButton = new ControlButton(center, leftTop,rightTop);
    }

    void onTouchEvent(int x, int y){
        if(leftButton.pressed(x,y))
            field.moveFigureLeft();
        if(rightButton.pressed(x,y))
            field.moveFigureRight();
        if(rotationButton.pressed(x,y))
            field.rotateFigure();
        if(speedUpButton.pressed(x,y))
            field.speedUpFalling();
    }

    void drawInterface(Canvas canvas) {
        leftButton.draw(canvas, Color.argb(130, 100, 149, 237));
        rightButton.draw(canvas, Color.argb(130,100, 149, 237));
        rotationButton.draw(canvas, Color.argb(130,173, 255, 47));
        speedUpButton.draw(canvas, Color.argb(130,250, 128, 114));
    }

    private void printScoredPoints(Canvas canvas) {
        String scoredPoints = String.valueOf(field.getScoredPoints());
        paint.setColor(Color.BLACK);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText(scoredPoints, 50, 50, paint);
    }

    private class ControlButton {
        private Path path = new Path();
        private Region region = new Region();

        ControlButton(Point... tops) {
            path.moveTo(tops[0].x, tops[0].y);
            for (int i = 1; i < tops.length; i++) {
                path.lineTo(tops[i].x,tops[i].y);
            }
            path.close();
            region.setPath(path, fullScreenRegion);
        }

        void draw(Canvas canvas, int color){
//            paint.setColor(Color.BLACK);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(10);
//
//            canvas.drawPath(path, paint);

            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawPath(path, paint);
        }

        boolean pressed(int x, int y){
            return region.contains(x,y);
        }
    }
}
