package com.octagisgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.octagisgame.FieldDrawers.ClassicFieldDrawer;
import com.octagisgame.FieldDrawers.FieldDrawer;
import com.octagisgame.FieldDrawers.PolygonFieldDrawer;
import com.octagisgame.model.PlayingField;

public class GameActivity extends AppCompatActivity {
    FieldDrawer fieldDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        PlayingField playingField = new PlayingField(10, 15);
        fieldDrawer = new ClassicFieldDrawer(playingField, displayMetrics);
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            fieldDrawer.drawField(canvas);
        }
    }
}