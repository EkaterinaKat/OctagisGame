package com.octagisgame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.drawers.ClassicFieldDrawer;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.model.PlayingField;

public class GameActivity extends AppCompatActivity {
    FieldDrawer fieldDrawer;
    PlayingField playingField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int numberOfColumns = 20;
        int numberOfRows = 20;
        playingField = new PlayingField(numberOfColumns, numberOfRows, drawView);
        fieldDrawer = new ClassicFieldDrawer(playingField, displayMetrics);
        playingField.startGame();
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            fieldDrawer.drawField(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            fieldDrawer.onTouchEvent(x, y);

            return super.onTouchEvent(event);
        }
    }
}
