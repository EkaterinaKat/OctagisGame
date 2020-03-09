package com.octagisgame.model;

import java.util.TreeSet;

public interface Database {
    TreeSet<Score> getScores();

    void addScore(Score score);

    void deleteScores();
}
