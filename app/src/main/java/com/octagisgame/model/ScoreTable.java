package com.octagisgame.model;

import java.util.Iterator;
import java.util.TreeSet;

public class ScoreTable {
    private static int SCORE_TABLE_SIZE = 10;
    private Database database;
    private TreeSet<Score> scores;

    public ScoreTable(Database database) {
        this.database = database;
        scores = getScoresFromDatabase();
    }

    private TreeSet<Score> getScoresFromDatabase() {
        return database.getScores();
    }

    public TreeSet<Score> getScores() {
        return scores;
    }

    public void addScore(Score score) {
        scores.add(score);
        if (scores.size() > SCORE_TABLE_SIZE)
            scores.remove(scores.first());
        refreshTableInDatabase();
    }

    private void refreshTableInDatabase() {
        database.deleteScores();
        Iterator<Score> iterator = scores.iterator();
        for (Score score : scores) {
            database.addScore(score);
        }
    }
}
