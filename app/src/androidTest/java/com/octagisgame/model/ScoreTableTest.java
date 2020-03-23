package com.octagisgame.model;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.octagisgame.database.ScoresSQLiteDb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class ScoreTableTest {
    private ScoreTable table;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        Database database = new ScoresSQLiteDb(context);
        database.deleteScores();
        ScoreTable.create(database, "player1");
        table = ScoreTable.getInstance();
    }

    @Test
    public void addScore() {
        int moreThanTableSize = ScoreTable.SIZE + 1;
        for (int i = 0; i < moreThanTableSize; i++) {
            table.addScore(i);
        }
        Set<Score> scores = table.getScores();
        for (Score score : scores) {
            Assert.assertTrue(score.getScoredPoints() != 0);
        }
    }
}