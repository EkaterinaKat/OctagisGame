package com.octagisgame.model;

import androidx.annotation.NonNull;

public class Score implements Comparable {
    private String player;
    private int scoredPoints;

    public Score(String player, int scoredPoints) {
        this.player = player;
        this.scoredPoints = scoredPoints;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Score another = (Score) o;
        Integer thisScoredPoints = this.scoredPoints;
        return thisScoredPoints.compareTo(another.scoredPoints);
    }

    public String getPlayer() {
        return player;
    }

    public Integer getScoredPoints() {
        return scoredPoints;
    }
}
