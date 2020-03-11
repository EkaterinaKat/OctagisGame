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

import com.octagisgame.builders.GameBuilder;
import com.octagisgame.controller.Game;
import com.octagisgame.controller.controlinterfaces.ControlInterface;
import com.octagisgame.dialogs.PauseDialog;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.stylers.BrickStyler;

import static com.octagisgame.activities.MainActivity.hideSystemUI;

public class GameActivity extends AppCompatActivity {
    private DrawView drawView;
    private DrawThread drawThread;
    private Game game;
    private FieldDrawer fieldDrawer;
    private ControlInterface controlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);

        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getRealSize(displaySize);

        GameBuilder gameBuilder = new GameBuilder(this, displaySize);
        gameBuilder
                .setMode(GameBuilder.Mode.POLYGON)
                .setStyler(new BrickStyler())
                .build();
        game = gameBuilder.getGame();
        fieldDrawer = gameBuilder.getFieldDrawer();
        controlInterface = gameBuilder.getControlInterface();

        drawThread = new DrawThread();
        drawThread.start();
        startGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(getWindow());
    }

    @Override
    public void onPause() {
        super.onPause();
        game.setOnPause();
    }

    @Override
    public void onBackPressed() {
        game.setOnPause();
    }

    public void continueGame() {
        game.continueGame();
    }

    public void startGame() {
        game.start();
    }

    public void showPauseDialog() {
        PauseDialog pauseDialog = new PauseDialog(this);
        pauseDialog.show(getSupportFragmentManager(), "pauseDialog");
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
            controlInterface.onTouchEvent((int) x, (int) y);
            return super.onTouchEvent(event);
        }
    }
}
