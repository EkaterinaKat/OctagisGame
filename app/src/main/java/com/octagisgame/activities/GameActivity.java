package com.octagisgame.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.dialogs.PauseDialog;
import com.octagisgame.drawers.ClassicFieldDrawer;
import com.octagisgame.drawers.FieldDrawer;
import com.octagisgame.model.PlayingField;

import static com.octagisgame.activities.MainActivity.hideSystemUI;

public class GameActivity extends AppCompatActivity {
    private FieldDrawer fieldDrawer;
    private DrawView drawView;
    private PlayingField playingField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);
        hideSystemUI(getWindow());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int numberOfColumns = 15;
        int numberOfRows = 17;
        playingField = new PlayingField(this, numberOfColumns, numberOfRows);
        fieldDrawer = new ClassicFieldDrawer(playingField, displayMetrics);
//        fieldDrawer = new PolygonFieldDrawer(playingField, displayMetrics);
        startGame();
    }

    @Override
    public void onBackPressed() {
        playingField.pauseGame();
        showPauseDialog();
    }

    public void continueGame(){
        playingField.continueGame();
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

    private void showPauseDialog() {
        PauseDialog pauseDialog = new PauseDialog(this);
        pauseDialog.show(getSupportFragmentManager(), "pauseDialog");
    }

    public DrawView getDrawView() {
        return drawView;
    }
}
