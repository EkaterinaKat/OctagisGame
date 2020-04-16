package com.octagisgame.services.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.octagisgame.services.database.ScoresDbSchema.ScoreTable;
import com.octagisgame.services.database.ScoresDbSchema.ScoreTable.Cols;
import com.octagisgame.model.Database;
import com.octagisgame.model.Score;

import java.util.TreeSet;

public class ScoresSQLiteDb implements Database {
    private SQLiteDatabase database;

    public ScoresSQLiteDb(Context context) {
        database = new ScoresSQLiteDbHelper(context).getWritableDatabase();
    }

    @Override
    public void addScore(Score score) {
        ContentValues values = getContentValues(score);
        database.insert(ScoreTable.NAME, null, values);
    }

    @Override
    public void deleteScores() {
        database.delete(ScoreTable.NAME, null, null);
    }

    @Override
    public TreeSet<Score> getScores() {
        TreeSet<Score> scores = new TreeSet<>();
        try (ScoreCursorWrapper cursor = getScoreTableCursor()) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                scores.add(cursor.getScore());
                cursor.moveToNext();
            }
        }
        return scores;
    }

    private ScoreCursorWrapper getScoreTableCursor() {
        Cursor cursor = database.query(ScoreTable.NAME, null, null, null,
                null, null, null);
        return new ScoreCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Score score) {
        ContentValues values = new ContentValues();
        values.put(Cols.PLAYER, score.getPlayer());
        values.put(Cols.SCORED_POINTS, score.getScoredPoints());
        return values;
    }

    private class ScoreCursorWrapper extends CursorWrapper {
        ScoreCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        Score getScore() {
            String player = getString(getColumnIndex(ScoreTable.Cols.PLAYER));
            int scoredPoints = getInt(getColumnIndex(ScoreTable.Cols.SCORED_POINTS));
            return new Score(player, scoredPoints);
        }

    }
}
