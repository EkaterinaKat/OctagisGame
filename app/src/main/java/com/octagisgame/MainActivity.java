package com.octagisgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(startGame);
    }

    private final View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startBtn.setImageResource(R.drawable.play_pressed);
            Intent intent = new Intent(MainActivity.this,GameActivity.class);
            startActivity(intent);
        }
    };
}
