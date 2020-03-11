package com.octagisgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;
import com.octagisgame.database.ScoresSQLiteDb;
import com.octagisgame.model.ScoreTable;

public class MainActivity extends AppCompatActivity {
    private ImageView startBtn;
    private ImageView scoresBtn;
    private String currentPlayer = "somePlayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScoreTable.create(new ScoresSQLiteDb(this), currentPlayer);

        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(startGame);

        scoresBtn = findViewById(R.id.score_table_button);
        scoresBtn.setOnClickListener(showScoresTable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(getWindow());
    }

    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startBtn.setImageResource(R.drawable.play_btn_pressed);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private final View.OnClickListener showScoresTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ScoreTableActivity.class);
            startActivity(intent);
        }
    };

    public static void hideSystemUI(Window window) {
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
