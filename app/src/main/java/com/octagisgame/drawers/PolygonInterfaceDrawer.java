package com.octagisgame.drawers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.octagisgame.controller.ControlInterface;
import com.octagisgame.controller.PolygonControlInterface;

import java.util.ArrayList;
import java.util.List;

public class PolygonInterfaceDrawer {
    /* Указывает сколько процентов от высоты поля занимает высота кнопок управления */
    private final int TEXT_SIZE = 40;
    private Paint paint = new Paint();
    private List<PolygonControlInterface.ControlButton> buttons = new ArrayList<>();

    public PolygonInterfaceDrawer(ControlInterface controlInterface) {
        buttons = ((PolygonControlInterface) controlInterface).getButtons();
    }

    void drawInterface(Canvas canvas) {
        for (PolygonControlInterface.ControlButton button : buttons) {
            button.draw(canvas);
        }
    }

//    private void printScoredPoints(Canvas canvas) {   //todo сделать что-то с этим
//        String scoredPoints = String.valueOf(field.getScoredPoints());
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(TEXT_SIZE);
//        canvas.drawText(scoredPoints, 50, 50, paint);
//    }
}
