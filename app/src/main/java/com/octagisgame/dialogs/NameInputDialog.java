package com.octagisgame.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.octagisgame.R;
import com.octagisgame.activities.MainActivity;

import static com.octagisgame.activities.MainActivity.hideSystemUI;

public abstract class NameInputDialog extends DialogFragment {
    Button okButton;
    MainActivity activity;
    EditText editText;
    TextView messageTextView;

    NameInputDialog(MainActivity activity) {
        this.activity = activity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_name_input, null);
        messageTextView = v.findViewById(R.id.message_text_view);
        editText = v.findViewById(R.id.player_name_edit);
        editText.addTextChangedListener(textWatcher);
        okButton = v.findViewById(R.id.ok_button);
        okButton.setEnabled(false);
        return v;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        hideSystemUI(activity.getWindow());
    }

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
