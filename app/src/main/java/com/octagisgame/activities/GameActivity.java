package com.octagisgame.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.controller.GameProcess;
import com.octagisgame.dialogs.PauseDialog;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.drawers.PolygonFieldDrawer;
import com.octagisgame.model.PlayingField;
import com.octagisgame.stylers.MinimalisticStyler;
import com.octagisgame.stylers.Styler;

import static com.octagisgame.activities.MainActivity.hideSystemUI;

public class GameActivity extends AppCompatActivity {
    private FieldDrawer fieldDrawer;
    private DrawView drawView;
    private PlayingField playingField;
    private DrawThread drawThread;
    private GameProcess game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);
        hideSystemUI(getWindow());

        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getRealSize(displaySize);

        int numberOfColumns = 15;
        int numberOfRows = 17;
        playingField = new PlayingField(numberOfColumns, numberOfRows);
        game = new GameProcess(this, playingField);
        Styler styler = new MinimalisticStyler();
        fieldDrawer = new PolygonFieldDrawer(playingField, displaySize, styler);
        drawThread = new DrawThread();
        drawThread.start();
        startGame();
    }

    @Override
    public void onPause(){
        super.onPause();
        game.setOnPause();
        showPauseDialog();
    }

    @Override
    public void onBackPressed() {
        game.setOnPause();
        showPauseDialog();
    }

    public void continueGame() {
        game.continueGame();
        hideSystemUI(getWindow());
    }

    public void startGame() {
        game.start();
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
            fieldDrawer.onTouchEvent((int) x, (int) y);
            return super.onTouchEvent(event);
        }
    }

    public void goToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        drawThread.interrupt();
        finish();
    }

    private class DrawThread extends Thread {
        @Override
        public void run() {
            while (true) {
                if (!game.isPaused()) {
                    drawView.invalidate();
                }
            }
        }
    }

    private void showPauseDialog() {
        PauseDialog pauseDialog = new PauseDialog(this);
        pauseDialog.show(getSupportFragmentManager(), "pauseDialog");
    }

    public DrawView getDrawView() {
        return drawView;
    }
}
