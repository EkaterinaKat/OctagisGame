package com.octagisgame.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.octagisgame.R;
import com.octagisgame.model.Score;
import com.octagisgame.model.ScoreTable;

import java.util.Set;

import static com.octagisgame.activities.MainActivity.hideSystemUI;

public class ScoreTableActivity extends AppCompatActivity {
    private final int PADDING = 20;
    private final int TEXT_SIZE = 20;
    private ScoreTable scoreTable;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);
        tableLayout = findViewById(R.id.tableLayout);
        scoreTable = ScoreTable.getInstance();
        findViewById(R.id.back_button).setOnClickListener(backToMainMenu);
    }

    View.OnClickListener backToMainMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        clearTable();
        fillTable();
        hideSystemUI(getWindow());
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
