package com.octagisgame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;
import com.octagisgame.dialogs.InitialNameInputDialog;
import com.octagisgame.dialogs.NameChangeDialog;
import com.octagisgame.dialogs.NameInputDialog;
import com.octagisgame.model.ScoreTable;
import com.octagisgame.services.PreferencesManager;
import com.octagisgame.services.SoundManager;
import com.octagisgame.services.database.ScoresSQLiteDb;
import com.octagisgame.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private ImageView startButton;
    private ImageView scoresButton;
    private ImageView settingsButton;
    private String playerName;
    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferencesManager.create(getPreferences(MODE_PRIVATE));
        SoundManager.create(this);
        setSoundModeFromPreferences();
        tuneComponents();
        loadPlayerNameFromPreferences();
        ScoreTable.create(new ScoresSQLiteDb(this), playerName);
    }

    private void setSoundModeFromPreferences() {
        boolean soundOn = PreferencesManager.getInstance().loadSoundMode();
        SoundManager.getInstance().setSoundOn(soundOn);
    }

    private void tuneComponents() {
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(startButtonListener);
        scoresButton = findViewById(R.id.score_table_button);
        scoresButton.setOnClickListener(scoresButtonListener);
        settingsButton = findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(settingsButtonListener);
        greetingTextView = findViewById(R.id.greeting_text_view);
        greetingTextView.setOnClickListener(greetingTextViewListener);
    }

    private void loadPlayerNameFromPreferences() {
        playerName = PreferencesManager.getInstance().loadPlayerName();
        if (playerName.equals("")) {
            showNameInputDialog();
            greetingTextView.setVisibility(View.INVISIBLE);
        } else {
            greetingTextView.setText(getString(R.string.greeting, playerName));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.hideSystemUI(getWindow());
    }

    public void setNewPlayerName(String playerName) {
        this.playerName = playerName;
        PreferencesManager.getInstance().savePlayerName(playerName);
        ScoreTable.getInstance().changePlayer(playerName);
        greetingTextView.setText(getString(R.string.greeting, playerName));
        greetingTextView.setVisibility(View.VISIBLE);
    }

    private final View.OnClickListener startButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateStartButtonPressed();
            startGameActivity();
        }
    };

    private void animateStartButtonPressed() {
        Utils.changeImageTemporarily(startButton, R.drawable.play_btn_pressed);
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private final View.OnClickListener scoresButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateScoresButtonPressed();
            startScoreTableActivity();
        }
    };

    private void animateScoresButtonPressed() {
        Utils.changeImageTemporarily(scoresButton, R.drawable.scores_btn_pressed);
    }

    private void startScoreTableActivity() {
        Intent intent = new Intent(this, ScoreTableActivity.class);
        startActivity(intent);
    }

    private final View.OnClickListener settingsButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateSettingsButtonPressed();
            startSettingsActivity();
        }
    };

    private void animateSettingsButtonPressed() {
        Utils.changeImageTemporarily(settingsButton, R.drawable.settings_btn_pressed);
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener greetingTextViewListener = new View.OnClickListener() {
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
}
