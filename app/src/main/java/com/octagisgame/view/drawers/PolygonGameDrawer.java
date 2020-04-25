package com.octagisgame.view.drawers;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;

import com.octagisgame.controller.Game;
import com.octagisgame.controller.buttons.Button;
import com.octagisgame.controller.buttons.PauseButton;
import com.octagisgame.controller.controlinterfaces.ControlInterface;
import com.octagisgame.controller.controlinterfaces.PolygonControlInterface;
import com.octagisgame.view.painttuners.PaintTuner;

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PolygonGameDrawer extends GameDrawer {
    private List<Button> controlButtons;
    private PauseButton pauseButton;
    private Path[][] cellOutlines;

    public PolygonGameDrawer(ControlInterface controlInterface, Point displaySize, PaintTuner paintTuner) {
        super(displaySize, paintTuner);
        controlButtons = ((PolygonControlInterface) controlInterface).getControlButtons();
        pauseButton = ((PolygonControlInterface) controlInterface).getPauseButton();
        new Calculator().calculateAllRequiredCoordinates();
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
        for (Button button : controlButtons) {
            Path path = button.getPath();
            int color = button.getColor();
            canvas.drawPath(path, paintTuner.getControlButtonPaint(color));
            canvas.drawPath(path, paintTuner.getControlButtonBorderPaint());
        }
    }

    private void drawPauseButton(Canvas canvas) {
        int color = pauseButton.getColor();
        canvas.drawPath(pauseButton.getPath(), paintTuner.getPauseButtonPaint(color));
    }

    @Override
    void drawCell(int column, int row, Canvas canvas) {
        int cellColour = Game.getInstance().getCellColour(column, row);
        Path outline = cellOutlines[column][row];
        canvas.drawPath(outline, paintTuner.getCellPaint(cellColour));
        canvas.drawPath(outline, paintTuner.getCellBorderPaint());
    }

    private class Calculator {
        Point center;
        /* Угол в радианах, внутри которого располагается одна колонка*/
        double angle;
        int rowHeight;
        /* Главная ось направлена из центра поля вправо, узлы на главной оси это точки, которые
         * будем поворачивать на разные углы, чтобы выполнять посторения */
        Point[] mainAxisNodes;
        int columnHeight;

        void calculateAllRequiredCoordinates() {
            calculateGeneralParameters();
            calculateMainAxisNodesCoordinates();
            calculateCellsOutlinesCoordinates();
        }

        void calculateGeneralParameters() {
            columnHeight = screenWidth / 2;
            rowHeight = columnHeight / (numberOfRows + 1);
            angle = 2 * PI / numberOfColumns;
            center = new Point(screenWidth / 2, columnHeight);
        }

        void calculateMainAxisNodesCoordinates() {
            mainAxisNodes = new Point[numberOfRows + 1];
            for (int i = 0; i < mainAxisNodes.length; i++) {
                mainAxisNodes[i] = new Point(center.x, center.y + columnHeight - i * rowHeight);
            }
        }

        void calculateCellsOutlinesCoordinates() {
            cellOutlines = new Path[numberOfColumns][numberOfRows];
            for (int row = 0; row < numberOfRows; row++) {
                for (int column = 0; column < numberOfColumns; column++) {
                    cellOutlines[column][row] = getCellOutline(column, row);
                }
            }
        }

        Path getCellOutline(int column, int row) {
            Point[] tops = getCellTops(column, row);
            Path outline = new Path();
            outline.moveTo(tops[0].x, tops[0].y);
            for (int i = 1; i < tops.length; i++) {
                outline.lineTo(tops[i].x, tops[i].y);
            }
            outline.close();
            return outline;
        }

        Point[] getCellTops(int column, int row) {
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
}
