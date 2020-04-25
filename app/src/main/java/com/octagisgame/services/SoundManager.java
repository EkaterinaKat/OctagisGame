package com.octagisgame.services;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.octagisgame.R;

public class SoundManager {
    private final int MAX_SOUND_STREAMS = 5;
    private final int PRIORITY = 1;
    private static SoundManager instance;
    private int volume = 1;
    private SoundPool soundPool;
    private int moveSoundID;
    private int accelerationSoundID;
    private int gameOverSoundID;
    private int rowDeletionSoundID;
    private int clickSoundID;

    private SoundManager(Context context) {
        soundPool = new SoundPool(MAX_SOUND_STREAMS, AudioManager.STREAM_MUSIC, 0);
        loadSounds(context);
    }

    public static void create(Context context) {
        if (instance == null) {
            instance = new SoundManager(context);
        }
    }

    public static SoundManager getInstance() {
        return instance;
    }

    public void setSoundOn(boolean soundOn) {
        if (soundOn)
            volume = 1;
        else
            volume = 0;
    }

    public boolean soundOn() {
        return volume == 1;
    }

    private void loadSounds(Context context) {
        moveSoundID = soundPool.load(context, R.raw.move_sound, PRIORITY);
        accelerationSoundID = soundPool.load(context, R.raw.acceleration_sound, PRIORITY);
        gameOverSoundID = soundPool.load(context, R.raw.game_over_sound, PRIORITY);
        rowDeletionSoundID = soundPool.load(context, R.raw.row_deletion_sound, PRIORITY);
        clickSoundID = soundPool.load(context, R.raw.click_sound, PRIORITY);
    }

    public void playMoveSound() {
        playSound(moveSoundID);
    }

    public void playAccelerationSound() {
        playSound(accelerationSoundID);
    }

    public void playGameOverSound() {
        playSound(gameOverSoundID);
    }

    public void playRowDeletionSound() {
        playSound(rowDeletionSoundID);
    }

    public void playClickSound() {
        playSound(clickSoundID);
    }

    private void playSound(int id) {
        soundPool.play(id, volume, volume, PRIORITY, 0, 1);
    }
}
