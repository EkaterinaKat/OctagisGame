package com.octagisgame.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.octagisgame.R;
import com.octagisgame.activities.MainActivity;
import com.octagisgame.services.SoundManager;

public class InitialNameInputDialog extends NameInputDialog {
    private boolean nameWasEntered = false;

    public InitialNameInputDialog(MainActivity activity) {
        super(activity);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        okButton.setOnClickListener(listener);
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            if (!nameWasEntered) {
                activity.setNewPlayerName(editText.getText().toString());
                nameWasEntered = true;
                messageTextView.setText(R.string.name_change_hint);
                editText.setVisibility(View.GONE);
            } else {
                dismiss();
            }
        }
    };
}
