package com.octagisgame.model;

import java.util.Iterator;
import java.util.TreeSet;

class ScoreTable {
    static int SIZE = 10;
    private Database database;
    private TreeSet<Score> scores;

    ScoreTable(Database database) {
        this.database = database;
        scores = getScoresFromDatabase();
    }

    private TreeSet<Score> getScoresFromDatabase() {
        return database.getScores();
    }

    TreeSet<Score> getScores() {
        return scores;
    }

    void addScore(Score score) {
        scores.add(score);
        if (scores.size() > SIZE)
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
