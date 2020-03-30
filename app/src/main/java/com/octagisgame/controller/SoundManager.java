package com.octagisgame.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.octagisgame.R;

public class SoundManager {
    private final int MAX_SOUND_STREAMS = 5;
    private final int PRIORITY = 1;
    private static SoundManager instance;
    private SoundPool soundPool;
    private int moveSoundID;
    private int speedUpSoundID;
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

    private void loadSounds(Context context) {
        moveSoundID = soundPool.load(context, R.raw.move_sound, PRIORITY);
        speedUpSoundID = soundPool.load(context, R.raw.speed_up_sound, PRIORITY);
        gameOverSoundID = soundPool.load(context, R.raw.game_over_sound, PRIORITY);
        rowDeletionSoundID = soundPool.load(context, R.raw.row_deletion_sound, PRIORITY);
        clickSoundID = soundPool.load(context, R.raw.click_sound, PRIORITY);
    }

    void playMoveSound() {
        playSound(moveSoundID);
    }

    void playSpeedUpSound() {
        playSound(speedUpSoundID);
    }

    void playGameOverSound() {
        playSound(gameOverSoundID);
    }

    void playRowDeletionSound() {
        playSound(rowDeletionSoundID);
    }

    public void playClickSound() {
        playSound(clickSoundID);
    }

    private void playSound(int id) {
        soundPool.play(id, 1, 1, PRIORITY, 0, 1);
    }
}
