package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefinder.utils.NetworkUtils;
import com.example.android.moviefinder.utils.TheMovieDatabaseJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class TrailerAsyncTaskLoader extends AsyncTaskLoader<String[]> {
    private String[] trailerData = null;
    private int movieId;

    public TrailerAsyncTaskLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (trailerData != null) {
            deliverResult(trailerData);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(String[] data) {
        trailerData = data;
        super.deliverResult(data);
    }

    @Override
    public String[] loadInBackground() {
        URL movieTrailerUrl = NetworkUtils.buildUrlFetchTrailersById(movieId);

        try {
            String movieTrailerResponse = NetworkUtils.getResponseFromHttpUrl(movieTrailerUrl);

            String[] simpleTrailersData = TheMovieDatabaseJsonUtils.getTrailersFromJson(movieTrailerResponse);
            return simpleTrailersData;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
