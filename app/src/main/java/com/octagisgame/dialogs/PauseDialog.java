package com.octagisgame.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.octagisgame.R;
import com.octagisgame.activities.GameActivity;
import com.octagisgame.activities.MainActivity;

public class PauseDialog  extends DialogFragment {

    private GameActivity activity;

    public PauseDialog(GameActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.continue_game, continueGameListener)
                .setNegativeButton(R.string.main_menu, mainMenuListener);
        setCancelable(false);
        return builder.create();
    }

    private DialogInterface.OnClickListener continueGameListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dismiss();
            activity.continueGame();
        }
    };

    private DialogInterface.OnClickListener mainMenuListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            activity.goToMainMenu();
        }
    };

}
