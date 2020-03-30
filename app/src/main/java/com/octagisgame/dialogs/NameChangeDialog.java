package com.octagisgame.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.octagisgame.activities.MainActivity;
import com.octagisgame.controller.SoundManager;

public class NameChangeDialog extends NameInputDialog {

    public NameChangeDialog(MainActivity activity) {
        super(activity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        okButton.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            activity.setNewPlayerName(editText.getText().toString());
            dismiss();
        }
    };
}
