package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Point;

import com.octagisgame.model.PlayingField;
import com.octagisgame.stylers.Styler;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PolygonFieldDrawer extends FieldDrawer {
    /* Угол в радианах, внутри которого располагается одна колонка*/
    private double angle;
    private int rowHeight;
    private Point center;
    /* Главная ось направлена из центра поля вправо, узлы на главной оси это точки, которые
     * будем поворачивать на разные углы чтобы выполнять посторения */
    private Point[] mainAxisNodes;
    private int columnHeight;
    private PolygonInterfaceDrawer interfaceDrawer;

    public PolygonFieldDrawer(PlayingField field, PolygonInterfaceDrawer interfaceDrawer, Point displaySize, Styler styler) {
        super(field, displaySize, styler);
        this.interfaceDrawer = interfaceDrawer;
        angle = 2 * PI / numberOfColumns;
        columnHeight = screenWidth / 2;
        rowHeight = columnHeight / (numberOfRows + 1);
        center = new Point(screenWidth / 2, columnHeight);
        setMainAxisNodes();
    }

    private void setMainAxisNodes() {
        mainAxisNodes = new Point[numberOfRows + 1];
        for (int i = 0; i < mainAxisNodes.length; i++) {
            mainAxisNodes[i] = new Point(center.x, center.y + columnHeight - i * rowHeight);
        }
    }

    @Override
    void drawInterface(Canvas canvas) {
        interfaceDrawer.drawInterface(canvas);
    }

    @Override
    void drawCell(int column, int row, Canvas canvas) {
        Point[] tops = getCellTops(column, row);
        path.moveTo(tops[0].x, tops[0].y);
        for (int i = 1; i < tops.length; i++) {
            path.lineTo(tops[i].x, tops[i].y);
        }
        path.close();
        int cellColour = field.getCellColour(column, row);
        styler.tunePaintForCell(paint, cellColour);
        canvas.drawPath(path, paint);
        styler.tunePaintForCellBorders(paint, cellColour);
        canvas.drawPath(path, paint);
        path.reset();
    }

    private Point[] getCellTops(int column, int row) {
        Point[] tops = new Point[4];
        tops[0] = rotatePointAroundCenter(mainAxisNodes[row], angle * column);
        tops[1] = rotatePointAroundCenter(mainAxisNodes[row + 1], angle * column);
        tops[2] = rotatePointAroundCenter(mainAxisNodes[row + 1], angle * (column + 1));
        tops[3] = rotatePointAroundCenter(mainAxisNodes[row], angle * (column + 1));
        return tops;
    }

    private Point rotatePointAroundCenter(Point point, double angle) {
        double x1 = (point.x - center.x) * cos(angle) + (point.y - center.y) * sin(angle) + center.x;
        double y1 = (-1) * (point.x - center.x) * sin(angle) + (point.y - center.y) * cos(angle) + center.y;
        return new Point((int) x1, (int) y1);
    }
}
