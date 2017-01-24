package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefinder.utils.TheMovieDatabaseJsonUtils;
import com.example.android.moviefinder.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class ReviewsAsyncTaskLoader extends AsyncTaskLoader<String[]> {
    private String[] reviewsData = null;
    private int movieId;

    public ReviewsAsyncTaskLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (reviewsData != null) {
            deliverResult(reviewsData);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(String[] data) {
        reviewsData = data;
        super.deliverResult(data);
    }

    @Override
    public String[] loadInBackground() {
        URL movieReviewUrl = NetworkUtils.buildUrlFetchReviewsById(movieId);

        try {
            String movieReviewResponse = NetworkUtils.getResponseFromHttpUrl(movieReviewUrl);

            String[] simpleReviewData = TheMovieDatabaseJsonUtils.getReviewsFromJson(movieReviewResponse);
            return simpleReviewData;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
