package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class PolygonFieldDrawer extends FieldDrawer {
    private double angleRad;
    private int rowHeight;
    private Point center;
    private Point[] mainAxisNodes;
    private int columnHeight;

    public PolygonFieldDrawer(PlayingField field, DisplayMetrics displayMetrics) {
        super(field, displayMetrics);
        angleRad = 2 * PI / numberOfColumns;
        columnHeight = screenHeight / 2;
        rowHeight = columnHeight / (numberOfRows + 1);
        center = new Point(screenWidth / 2, screenHeight / 2);
        setMainAxisNodes();
    }

    private void setMainAxisNodes() {
        //главная ось направлена из центра поля вправо, узлы на главной оси это точки которые
        //будем поворачивать на разные углы чтобы выполнять посторения
        mainAxisNodes = new Point[numberOfRows + 1];
        for (int i = 0; i < mainAxisNodes.length; i++) {
            mainAxisNodes[i] = new Point(center.x - columnHeight + i * rowHeight, center.y);
        }
    }

    @Override
    void drawCell(int column, int row, Canvas canvas) {
        Point[] tops = getCellTops(column, row);
        path.moveTo(tops[0].x, tops[0].y);
        for (int i = 1; i < tops.length; i++) {
            path.lineTo(tops[i].x, tops[i].y);
        }
        path.close();
        paint.setColor(field.getCellColour(column, row));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        canvas.drawPath(path, paint);
        path.reset();
    }

    private Point[] getCellTops(int column, int row) {
        Point[] tops = new Point[4];
        tops[0] = rotatePointAroundCenter(mainAxisNodes[row], angleRad * column);
        tops[1] = rotatePointAroundCenter(mainAxisNodes[row + 1], angleRad * column);
        tops[2] = rotatePointAroundCenter(mainAxisNodes[row + 1], angleRad * (column + 1));
        tops[3] = rotatePointAroundCenter(mainAxisNodes[row], angleRad * (column + 1));
        return tops;
    }

    private Point rotatePointAroundCenter(Point point, double angle) {
        double x1 = (point.x - center.x) * cos(angle) + (point.y - center.y) * sin(angle) + center.x;
        double y1 = (-1) * (point.x - center.x) * sin(angle) + (point.y - center.y) * cos(angle) + center.y;
        return new Point((int) x1, (int) y1);
    }

    @Override
    public void onTouchEvent(float x, float y) {
        if (pointInFieldArea((int) x, (int) y)) {
            field.rotateFigure();
        } else if (x > center.x) {
            field.moveFigureLeft();
        } else {
            field.moveFigureRight();
        }
    }

    private boolean pointInFieldArea(int x, int y) {
        return sqrt(pow(center.x - x, 2) + pow(center.y - y, 2)) <= columnHeight;
    }
}
