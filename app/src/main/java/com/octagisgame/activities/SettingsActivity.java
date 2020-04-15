package com.octagisgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.octagisgame.R;
import com.octagisgame.controller.SoundManager;

import static android.widget.CompoundButton.OnCheckedChangeListener;
import static com.octagisgame.activities.MainActivity.hideSystemUI;

public class SettingsActivity extends AppCompatActivity {
    private boolean soundOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideSystemUI(getWindow());

        findViewById(R.id.back_button).setOnClickListener(onBackPressed);

        SwitchCompat soundSwitch = findViewById(R.id.sound_switch);
        soundOn = SoundManager.getInstance().soundOn();
        soundSwitch.setChecked(soundOn);
        soundSwitch.setOnCheckedChangeListener(soundSwitchListener);
    }

    @Override
    protected void onDestroy() {
        saveSettings();
        super.onDestroy();
    }

    View.OnClickListener onBackPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            SoundManager.getInstance().playClickSound();
        }
    };

    OnCheckedChangeListener soundSwitchListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            soundOn = b;
            applySettings();
            SoundManager.getInstance().playClickSound();
        }
    };

    private void applySettings() {
        if (soundOn) {
            SoundManager.getInstance().turnOnSound();
        } else {
            SoundManager.getInstance().turnOffSound();
        }
    }

    private void saveSettings() {
        //todo обращаемся к преференсис
    }
}
