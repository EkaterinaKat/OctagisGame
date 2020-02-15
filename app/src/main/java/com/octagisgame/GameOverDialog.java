package com.octagisgame;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class GameOverDialog extends DialogFragment {

    private int scoredPoints;
    private GameActivity activity;

    GameOverDialog(GameActivity activity, int scoredPoints) {
        this.scoredPoints = scoredPoints;
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.game_over)
                .setMessage(getString(R.string.number_of_scored_points, scoredPoints))
                .setPositiveButton(R.string.start_again, startAgainListener)
                .setNegativeButton(R.string.main_menu, mainMenuListener);
        setCancelable(false);
        return adb.create();
    }

    private DialogInterface.OnClickListener startAgainListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                activity.startGame();
        }
    };

    private DialogInterface.OnClickListener mainMenuListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(activity, MainActivity.class); //todo озможно стоит этот код перенести в другое место
            startActivity(intent);
        }
    };
}
