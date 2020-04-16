package com.octagisgame.services.database;

class ScoresDbSchema {
    static final class ScoreTable {
        static final String NAME = "scores";

        static final class Cols {
            static final String PLAYER = "player";
            static final String SCORED_POINTS = "score";
        }
    }
}
