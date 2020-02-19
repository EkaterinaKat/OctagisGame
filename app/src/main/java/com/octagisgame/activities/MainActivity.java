package com.octagisgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;

public class MainActivity extends AppCompatActivity {
    private ImageView startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideSystemUI();

        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(startGame);
    }

    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startBtn.setImageResource(R.drawable.play_pressed);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        }
    };

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
}
