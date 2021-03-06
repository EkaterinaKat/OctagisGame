package com.octagisgame.model;

import java.util.TreeSet;

public class ScoreTable {
    static int SIZE = 10;
    private static ScoreTable instance;
    private Database database;
    private TreeSet<Score> scores;
    private String playerName;

    private ScoreTable(Database database, String playerName) {
        this.database = database;
        this.playerName = playerName;
        scores = getScoresFromDatabase();
    }

    public static void create(Database database, String currentPlayer) {
        if (instance == null) {
            instance = new ScoreTable(database, currentPlayer);
        }
    }

    public static ScoreTable getInstance() {
        return instance;
    }

    private TreeSet<Score> getScoresFromDatabase() {
        return database.getScores();
    }

    public TreeSet<Score> getScores() {
        return scores;
    }

    public void addScore(int scoredPoints) {
        Score score = new Score(playerName, scoredPoints);
        scores.add(score);
        if (scores.size() > SIZE)
            scores.remove(scores.first());
        refreshTableInDatabase();
    }

    private void refreshTableInDatabase() {
        database.deleteScores();
        for (Score score : scores) {
            database.addScore(score);
        }
    }

    public void changePlayer(String player) {
        playerName = player;
    }
}
