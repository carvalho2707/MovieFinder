package com.example.android.moviefinder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class MovieFinderDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "moviefinder.db";
    public static final int DATABASE_VERSION = 7;

    public MovieFinderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createQuery =
                "CREATE TABLE " + MovieFinderContract.MovieFinderEntry.TABLE_NAME + " ( " +
                        MovieFinderContract.MovieFinderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_TMDBID + " INTEGER NOT NULL UNIQUE, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_TITLE + " TEXT, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_OVERVIEW + " TEXT, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_USER_RATE + " TEXT, " +
                        MovieFinderContract.MovieFinderEntry.COLUMN_POSTER + " TEXT, " +
                        " UNIQUE ( " + MovieFinderContract.MovieFinderEntry._ID + " ) ON CONFLICT REPLACE );";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieFinderContract.MovieFinderEntry.TABLE_NAME);
        onCreate(db);

    }
}
