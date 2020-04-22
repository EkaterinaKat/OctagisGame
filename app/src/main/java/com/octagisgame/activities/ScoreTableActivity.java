package com.octagisgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;
import com.octagisgame.model.Score;
import com.octagisgame.model.ScoreTable;
import com.octagisgame.services.SoundManager;
import com.octagisgame.utils.Utils;

import java.util.Set;

public class ScoreTableActivity extends AppCompatActivity {
    private final int PADDING = 20;
    private final int TEXT_SIZE = 20;
    private ScoreTable scoreTable;
    private TableLayout tableLayout;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);
        tableLayout = findViewById(R.id.tableLayout);
        scoreTable = ScoreTable.getInstance();
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(onBackPressed);
    }

    View.OnClickListener onBackPressed = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SoundManager.getInstance().playClickSound();
            animateBackButtonPressed();
            finish();
        }
    };

    private void animateBackButtonPressed() {
        Utils.changeImageTemporarily(backButton, R.drawable.back_btn_pressed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearTable();
        fillTable();
        Utils.hideSystemUI(getWindow());
    }

    private void clearTable() {
        tableLayout.removeAllViews();
    }

    private void fillTable() {
        Set<Score> scores = scoreTable.getScores().descendingSet();
        for (Score score : scores) {
            addScoreToTable(score);
        }
    }

    private void addScoreToTable(Score score) {
        TableRow row = new TableRow(this);
        addTextToRow(score.getPlayer(), row);
        addTextToRow(score.getScoredPoints().toString(), row);
        tableLayout.addView(row);
    }

    private void addTextToRow(String str, TableRow row) {
        TextView textView1 = new TextView(this);
        textView1.setText(str);
        textView1.setPadding(PADDING, PADDING, PADDING, PADDING);
        textView1.setTextSize(TEXT_SIZE);
        row.addView(textView1);
    }
}
