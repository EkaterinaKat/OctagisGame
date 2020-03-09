package com.octagisgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.octagisgame.R;
import com.octagisgame.database.ScoresSQLiteDb;
import com.octagisgame.model.Database;
import com.octagisgame.model.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScoreTableActivity extends AppCompatActivity {
    Set<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        Database database = ScoresSQLiteDb.getInstance(this);
        scores = database.getScores();
        fillTable();

        List<Score> scores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            scores.add(new Score("player"+i, i));
        }

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        int padding = 20;


        for(Score score: scores){
            TextView textView1 = new TextView(this);
            textView1.setText(score.getPlayer());
            textView1.setPadding(padding, padding, padding, padding);
            TextView textView2 = new TextView(this);
            textView2.setText(score.getScoredPoints().toString());
            textView2.setPadding(padding, padding, padding, padding);
            TableRow row = new TableRow(this);
            row.addView(textView1);
            row.addView(textView2);
            tableLayout.addView(row);
        }
    }

    private void fillTable(){

    }
}
