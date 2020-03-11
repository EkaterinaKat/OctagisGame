package com.octagisgame.model;

public class Score implements Comparable {
    private String player;
    private int scoredPoints;

    public Score(String player, int scoredPoints) {
        this.player = player;
        this.scoredPoints = scoredPoints;
    }

    @Override
    public int compareTo(Object o) {
        Score another = (Score) o;
        Integer thisScoredPoints = this.scoredPoints;
        int result = thisScoredPoints.compareTo(another.scoredPoints);
        if(result==0){
            result = this.player.compareTo(another.player);
        }
        return result;
    }

    public String getPlayer() {
        return player;
    }

    public Integer getScoredPoints() {
        return scoredPoints;
    }
}
