package com.example.android.moviefinder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.moviefinder.model.Movie;

import java.util.List;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class MovieRepositoryOperations implements MovieRepositoryInterface {
    private SQLiteOpenHelper dbhandler;
    private SQLiteDatabase database;

    public static final String LOGTAG = "MOVIE_MNGMNT_SYS";

    public MovieRepositoryOperations(Context context) {
        dbhandler = new MovieFinderDBHelper(context);
    }

    public void open() {
        Log.i(LOGTAG, "Database Opened");
        database = dbhandler.getWritableDatabase();


    }

    public void close() {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    @Override
    public void insertMovie(Movie movie) {

    }

    @Override
    public void updateMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(Movie movie) {

    }

    @Override
    public List<Movie> getAllMovies(Movie movie) {
        return null;
    }
}
