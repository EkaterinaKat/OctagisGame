package com.octagisgame.services;

import android.content.SharedPreferences;

public class PreferencesManager {
    private final String PLAYER_NAME_KEY = "player name";
    private static PreferencesManager instance;
    private SharedPreferences preferences;

    private PreferencesManager(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public static void create(SharedPreferences preferences){
        if (instance==null){
            instance = new PreferencesManager(preferences);
        }
    }

    public static PreferencesManager getInstance() {
        return instance;
    }

    public void savePlayerName(String playerName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PLAYER_NAME_KEY, playerName);
        editor.commit();
    }

    public String  loadPlayerName() {
        return preferences.getString(PLAYER_NAME_KEY, "");
    }
}
