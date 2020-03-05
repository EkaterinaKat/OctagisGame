package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.controller.buttons.ControlButton;
import com.octagisgame.controller.buttons.PauseButton;
import com.octagisgame.controller.controlinterfaces.ControlInterface;
import com.octagisgame.controller.controlinterfaces.PolygonControlInterface;
import com.octagisgame.stylers.Styler;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PolygonFieldDrawer extends FieldDrawer {
    /* Угол в радианах, внутри которого располагается одна колонка*/
    private double angle;
    private int rowHeight;
    private Point center;
    /* Главная ось направлена из центра поля вправо, узлы на главной оси это точки, которые
     * будем поворачивать на разные углы, чтобы выполнять посторения */
    private Point[] mainAxisNodes;
    private int columnHeight;
    private List<ControlButton> controlButtons;
    private PauseButton pauseButton;

    public PolygonFieldDrawer(Game game, ControlInterface controlInterface, Point displaySize, Styler styler) {
        super(game, displaySize, styler);
        controlButtons = ((PolygonControlInterface) controlInterface).getControlButtons();
        pauseButton = ((PolygonControlInterface) controlInterface).getPauseButton();
        setSizes();
        setMainAxisNodes();
    }

    private void setSizes() {
        columnHeight = screenWidth / 2;
        rowHeight = columnHeight / (numberOfRows + 1);
        angle = 2 * PI / numberOfColumns;
        center = new Point(screenWidth / 2, columnHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawInterface(canvas);
    }

    private void drawInterface(Canvas canvas) {
        drawControlButtons(canvas);
        drawPauseButton(canvas);
    }

    private void drawControlButtons(Canvas canvas) {
        for (ControlButton button : controlButtons) {
            Path path = button.getPath();
            int color = button.getColor();

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color);
            canvas.drawPath(path, paint);

            styler.tunePaintForButtonBorders(paint);
            canvas.drawPath(path, paint);
        }
    }

    private void drawPauseButton(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(pauseButton.getColor());
        canvas.drawPath(pauseButton.getPath(), paint);
    }

    private void setMainAxisNodes() {
        mainAxisNodes = new Point[numberOfRows + 1];
        for (int i = 0; i < mainAxisNodes.length; i++) {
            mainAxisNodes[i] = new Point(center.x, center.y + columnHeight - i * rowHeight);
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
        int cellColour = game.getCellColour(column, row);
        styler.tunePaintForCell(paint, cellColour);
        canvas.drawPath(path, paint);
        styler.tunePaintForCellBorders(paint);
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

    /* Поворачивает заданную точку на заданный угол по часовой стрелке вокруг оси,
     * которая находится в точке center */
    private Point rotatePointAroundCenter(Point point, double angle) {
        int x1 = (int) ((point.x - center.x) * cos(angle) + (-1) * (point.y - center.y) * sin(angle) + center.x);
        int y1 = (int) ((point.x - center.x) * sin(angle) + (point.y - center.y) * cos(angle) + center.y);
        return new Point(x1, y1);
    }
}
