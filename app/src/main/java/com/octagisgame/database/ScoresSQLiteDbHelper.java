package com.octagisgame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.octagisgame.database.ScoresDbSchema.ScoreTable;

public class ScoresSQLiteDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";

    ScoresSQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table " + ScoreTable.NAME + "(" +
                " id integer primary key autoincrement, " +
                ScoreTable.Cols.PLAYER + " STRING, " +
                ScoreTable.Cols.SCORED_POINTS + " INT )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
