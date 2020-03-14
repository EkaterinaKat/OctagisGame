package com.octagisgame.dialogs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.octagisgame.R;
import com.octagisgame.activities.MainActivity;

public class NameInputDialog extends DialogFragment {
    private MainActivity activity;
    private Button okButton;
    private EditText editText;

    public NameInputDialog(MainActivity activity) {
        this.activity = activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        View v = inflater.inflate(R.layout.dialog_name_input, null);
        editText = v.findViewById(R.id.player_name_edit);
        editText.addTextChangedListener(textWatcher);
        okButton = v.findViewById(R.id.ok_button);
        okButton.setOnClickListener(onClickListener);
        okButton.setEnabled(false);
        return v;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.setNewPlayerName(editText.getText().toString());
            dismiss();
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            boolean textEditIsEmpty = charSequence.toString().trim().isEmpty();
            if (textEditIsEmpty)
                okButton.setEnabled(false);
            else
                okButton.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}
