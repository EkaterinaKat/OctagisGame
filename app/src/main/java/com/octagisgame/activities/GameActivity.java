package com.octagisgame.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.dialogs.GameOverDialog;
import com.octagisgame.drawers.ClassicFieldDrawer;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.model.PlayingField;

public class GameActivity extends AppCompatActivity {
    FieldDrawer fieldDrawer;
    PlayingField playingField;
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);
        hideSystemUI();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int numberOfColumns = 15;
        int numberOfRows = 17;
        playingField = new PlayingField(this, numberOfColumns, numberOfRows);
        fieldDrawer = new ClassicFieldDrawer(playingField, displayMetrics);
//        fieldDrawer = new PolygonFieldDrawer(playingField, displayMetrics);
        startGame();
    }

    public void startGame() {
        playingField.startGame();
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            fieldDrawer.draw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            fieldDrawer.onTouchEvent(x, y);

            return super.onTouchEvent(event);
        }
    }

    public void showGameOverDialog(int scoredPoints) {
        GameOverDialog gameOverDialog = new GameOverDialog(this, scoredPoints);
        gameOverDialog.show(getSupportFragmentManager(), "gameOverDialog");
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public DrawView getDrawView() {
        return drawView;
    }
}
