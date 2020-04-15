package com.octagisgame.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;
import com.octagisgame.controller.SoundManager;
import com.octagisgame.database.ScoresSQLiteDb;
import com.octagisgame.dialogs.InitialNameInputDialog;
import com.octagisgame.dialogs.NameChangeDialog;
import com.octagisgame.dialogs.NameInputDialog;
import com.octagisgame.model.ScoreTable;

public class MainActivity extends AppCompatActivity {
    private final String PLAYER_NAME_KEY = "player name";
    private ImageView startButton;
    private ImageView scoresButton;
    private ImageView settingsButton;
    private String playerName;
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(onStartButtonPressed);
        scoresButton = findViewById(R.id.score_table_button);
        scoresButton.setOnClickListener(onScoresButtonPressed);
        settingsButton = findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(onSettingsButtonPressed);
        greetingTextView = findViewById(R.id.greeting_text_view);
        greetingTextView.setOnClickListener(onGreetingTextViewPressed);

        loadPlayerName();
        if (playerName.equals("")) {
            showNameInputDialog();
            greetingTextView.setVisibility(View.INVISIBLE);
        }
        greetingTextView.setText(getString(R.string.greeting, playerName));
        ScoreTable.create(new ScoresSQLiteDb(this), playerName);
        SoundManager.create(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI(getWindow());
    }

    private void loadPlayerName() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        playerName = preferences.getString(PLAYER_NAME_KEY, "");
    }

    public void setNewPlayerName(String playerName) {
        this.playerName = playerName;
        savePlayerName(playerName);
        ScoreTable.getInstance().changePlayer(playerName);
        greetingTextView.setText(getString(R.string.greeting, playerName));
        greetingTextView.setVisibility(View.VISIBLE);
    }

    private void savePlayerName(String playerName) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLAYER_NAME_KEY, playerName);
        editor.commit();
    }

    private final View.OnClickListener onStartButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateStartButtonPressed();
            startGameActivity();
        }
    };

    private void animateStartButtonPressed() {
        changeImageTemporarily(startButton, R.drawable.play_btn_pressed);
    }

    private void changeImageTemporarily(final ImageView imageView, int imageResource) {
        final Drawable drawable = imageView.getDrawable();
        imageView.setImageResource(imageResource);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imageView.setImageDrawable(drawable);
            }
        }).start();
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private final View.OnClickListener onScoresButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateScoresButtonPressed();
            startScoreTableActivity();
        }
    };

    private void animateScoresButtonPressed() {
        changeImageTemporarily(scoresButton, R.drawable.scores_btn_pressed);
    }

    private void startScoreTableActivity() {
        Intent intent = new Intent(this, ScoreTableActivity.class);
        startActivity(intent);
    }

    private final View.OnClickListener onSettingsButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateSettingsButtonPressed();
            startSettingsActivity();
        }
    };

    private void animateSettingsButtonPressed() {
        changeImageTemporarily(settingsButton, R.drawable.settings_btn_pressed);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener onGreetingTextViewPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            showNameChangeDialog();
        }
    };

    private void showNameInputDialog() {
        NameInputDialog nameInputDialog = new InitialNameInputDialog(this);
        nameInputDialog.show(getSupportFragmentManager(), null);
    }

    private void showNameChangeDialog() {
        NameInputDialog nameInputDialog = new NameChangeDialog(this);
        nameInputDialog.show(getSupportFragmentManager(), null);
    }

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
