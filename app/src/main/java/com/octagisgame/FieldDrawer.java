package com.octagisgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.DisplayMetrics;

import com.octagisgame.model.PlayingField;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

class FieldDrawer {
    private Path path = new Path();
    private Paint paint = new Paint();

    private int numberOfColumns;
    private int numberOfRows;
    private Point center;
    private int rowHeight;
    private double angleRad;
    private Point[] mainAxisNodes;

    FieldDrawer(PlayingField field, DisplayMetrics displayMetrics) {
        numberOfColumns = field.getNumberOfColumns();
        numberOfRows = field.getNumberOfRows();
        angleRad = 2 * PI / numberOfColumns;
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        int columnHeight = screenHeight / 2;
        rowHeight = columnHeight / numberOfRows;
        center = new Point(screenWidth / 2, screenHeight / 2);
        setMainAxisNodes();
    }

    private void setMainAxisNodes() {
        //главная ось направлена из центра поля вправо, узлы на главной оси это точки которые
        //будем поворачивать на разные углы чтобы выполнять посторения
        mainAxisNodes = new Point[numberOfRows+1];
        for (int i = 0; i < mainAxisNodes.length; i++) {
            mainAxisNodes[i] = new Point(center.x - i * rowHeight, center.y);
        }
    }

    void drawField(Canvas canvas) {
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                drawCell(i,j,canvas);
            }
        }
    }

    private void drawCell(int column, int row, Canvas canvas) {
        Point[] tops = getCellTops(column, row);
        path.moveTo(tops[0].x,tops[0].y);
        for (int i = 1; i < tops.length; i++) {
            path.lineTo(tops[i].x,tops[i].y);
        }
        path.close();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
        path.reset();
    }

    private Point[] getCellTops(int column, int row){
        Point[] tops = new Point[4];
        tops[0] = rotatePointAroundCenter(mainAxisNodes[row],angleRad*column);
        tops[1] = rotatePointAroundCenter(mainAxisNodes[row+1],angleRad*column);
        tops[2] = rotatePointAroundCenter(mainAxisNodes[row+1],angleRad*(column+1));
        tops[3] = rotatePointAroundCenter(mainAxisNodes[row],angleRad*(column+1));
        return tops;
    }

    private Point rotatePointAroundCenter(Point point, double angle) {
        double x1 = (point.x - center.x) * cos(angle) + (point.y - center.y) * sin(angle) + center.x;
        double y1 = (-1) * (point.x - center.x) * sin(angle) + (point.y - center.y) * cos(angle) + center.y;
        return new Point((int) x1, (int) y1);
    }
}
