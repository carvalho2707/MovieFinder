package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefinder.data.MovieFinderContract;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class FavoritesByTmdbIdAsyncTaskLoader extends AsyncTaskLoader<String[]> {
    private String[] movieData = null;
    private int movieId;

    public FavoritesByTmdbIdAsyncTaskLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }


    @Override
    protected void onStartLoading() {
        if (movieData != null) {
            deliverResult(movieData);
        } else {
            forceLoad();
        }
    }

    @Override
    public String[] loadInBackground() {
        String[] result = null;
        int count = 0;
        Cursor cursor = getContext().getContentResolver().query(MovieFinderContract.MovieFinderEntry.CONTENT_URI,
                new String[]{MovieFinderContract.MovieFinderEntry._ID},
                MovieFinderContract.MovieFinderEntry.COLUMN_TMDBID + "=?",
                new String[]{Integer.toString(movieId)},
                null);
        if (cursor != null) {
            result = new String[cursor.getCount()];
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(MovieFinderContract.MovieFinderEntry._ID));
                    result[count] = Long.toString(id);
                    count++;
                } while (cursor.moveToNext());
            }
        }
        return result;
    }

    @Override
    public void deliverResult(String[] data) {
        movieData = data;
        super.deliverResult(data);
    }
}
