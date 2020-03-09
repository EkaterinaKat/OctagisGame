package com.octagisgame.model;

public class Score  implements Comparable{
    private String player;
    private int scoredPoints;

    public Score(String player, int scoredPoints){
        this.player = player;
        this.scoredPoints = scoredPoints;
    }

    @Override
    public int compareTo(Object o) {
        Score another = (Score)o;
        Integer scoredPoints = this.scoredPoints;
        return scoredPoints.compareTo(another.scoredPoints);
    }

    public String getPlayer() {
        return player;
    }

    public Integer getScoredPoints() {
        return scoredPoints;
    }
}
