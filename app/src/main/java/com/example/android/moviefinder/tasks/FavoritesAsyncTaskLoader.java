package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefinder.data.MovieFinderContract;
import com.example.android.moviefinder.model.Movie;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class FavoritesAsyncTaskLoader extends AsyncTaskLoader<Movie[]> {
    private Movie[] movieData = null;
    private MovieAsyncTaskLoaderListener mListener;


    public FavoritesAsyncTaskLoader(Context context, MovieAsyncTaskLoaderListener listener) {
        super(context);
        mListener = listener;
    }


    @Override
    protected void onStartLoading() {
        if (movieData != null) {
            deliverResult(movieData);
        } else {
            mListener.beforeExecute();
            forceLoad();
        }
    }

    @Override
    public Movie[] loadInBackground() {
        Movie[] result = null;
        int count = 0;
        Cursor cursor = getContext().getContentResolver().query(MovieFinderContract.MovieFinderEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            result = new Movie[cursor.getCount()];
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.setId(cursor.getLong(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry._ID)));
                    movie.setTmdbId(cursor.getInt(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_TMDBID)));
                    movie.setPosterUrl(cursor.getString(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_POSTER)));
                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_RELEASE_DATE)));
                    movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_OVERVIEW)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_TITLE)));
                    movie.setUserRate(cursor.getString(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry.COLUMN_USER_RATE)));
                    result[count] = movie;
                    count++;
                } while (cursor.moveToNext());
            }
        }
        return result;
    }

    @Override
    public void deliverResult(Movie[] data) {
        movieData = data;
        super.deliverResult(data);
    }
}
