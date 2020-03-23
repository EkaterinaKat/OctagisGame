package com.octagisgame.database;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.octagisgame.model.Score;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ScoresSQLiteDbTest {
    private ScoresSQLiteDb database;
    private Score score1;
    private Score score2;
    private Score score3;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        database = new ScoresSQLiteDb(context);

        score1 = new Score("player1", 1);
        score2 = new Score("player2", 2);
        score3 = new Score("player3", 3);

        database.addScore(score1);
        database.addScore(score3);
        database.addScore(score2);
    }

    @Test
    public void addScore() {
        Set<Score> actual = database.getScores();
        Set<Score> expected = new TreeSet<>();
        expected.add(score1);
        expected.add(score2);
        expected.add(score3);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteScores() {
        database.deleteScores();
        Set<Score> actual = database.getScores();
        Set<Score> expected = new TreeSet<>();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void scoresOrder() {
        Set<Score> scores = database.getScores();
        List<Integer> points = new ArrayList<>();
        for (Score score : scores) {
            points.add(score.getScoredPoints());
        }
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i) <= points.get(i - 1)) {
                Assert.fail("Неправильный порядок объектов Score");
            }
        }
    }
}