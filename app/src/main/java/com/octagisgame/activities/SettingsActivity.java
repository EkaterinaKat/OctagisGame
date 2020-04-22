package com.octagisgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.octagisgame.R;
import com.octagisgame.services.PreferencesManager;
import com.octagisgame.services.SoundManager;
import com.octagisgame.utils.Utils;

import static android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingsActivity extends AppCompatActivity {
    private boolean soundOn;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Utils.hideSystemUI(getWindow());

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(onBackPressed);

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
            animateBackButtonPressed();
            finish();
            SoundManager.getInstance().playClickSound();
        }
    };

    private void animateBackButtonPressed() {
        Utils.changeImageTemporarily(backButton, R.drawable.back_btn_pressed);
    }

    OnCheckedChangeListener soundSwitchListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            soundOn = b;
            applySettings();
            SoundManager.getInstance().playClickSound();
        }
    };

    private void applySettings() {
        SoundManager.getInstance().setSoundOn(soundOn);
    }

    private void saveSettings() {
        PreferencesManager.getInstance().saveSoundMode(soundOn);
    }
}
