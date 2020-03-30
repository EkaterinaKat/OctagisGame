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
import com.octagisgame.controller.SoundManager;
import com.octagisgame.controller.controlinterfaces.ControlInterface;
import com.octagisgame.dialogs.GameOverDialog;
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
    private boolean dialogOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);
        buildGame();
        drawThread = new DrawThread();
        drawThread.start();
        startGame();
    }

    private Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getRealSize(displaySize);
        return displaySize;
    }

    private void buildGame() {
        Point displaySize = getDisplaySize();
        GameBuilder gameBuilder = new GameBuilder(this, displaySize);
        gameBuilder
                .setMode(GameBuilder.Mode.POLYGON)
                .setStyler(new BrickStyler())
                .build();
        game = gameBuilder.getGame();
        fieldDrawer = gameBuilder.getFieldDrawer();
        controlInterface = gameBuilder.getControlInterface();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(getWindow());
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseGame();
    }

    @Override
    public void onBackPressed() {
        onPausePressed();
    }

    public void onPausePressed() { //todo этот метод нужен только потому что нужно воспроизводить звук
        SoundManager.getInstance().playClickSound();
        pauseGame();
    }

    public void pauseGame() {
        game.setOnPause();
        showPauseDialog();
    }

    public void continueGame() {
        game.continueGame();
    }

    public void startGame() {
        game.start();
    }

    public void showPauseDialog() {
        if (!dialogOpen) {
            PauseDialog pauseDialog = new PauseDialog(this);
            pauseDialog.show(getSupportFragmentManager(), null);
        }
    }

    public void showGameOverDialog(int scoredPoints) {
        GameOverDialog gameOverDialog = new GameOverDialog(this, scoredPoints);
        gameOverDialog.show(getSupportFragmentManager(), null);
    }

    public void goToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        drawThread.interrupt();
        finish();
    }

    public void setDialogOpen(boolean dialogOpen) {
        this.dialogOpen = dialogOpen;
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
