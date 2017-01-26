package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.utils.TheMovieDatabaseJsonUtils;
import com.example.android.moviefinder.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<Movie[]> {
    private Movie[] movieData = null;
    private String sort;
    private MovieAsyncTaskLoaderListener mListener;


    public MovieAsyncTaskLoader(Context context, MovieAsyncTaskLoaderListener listener, String sort) {
        super(context);
        mListener = listener;
        this.sort = sort;
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
        URL movieRequestUrl = NetworkUtils.buildUrl(sort);
        try {
            if (!NetworkUtils.isOnline(this.getContext())) {
                return null;
            }
            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

            Movie[] movieData = TheMovieDatabaseJsonUtils.getMovieInfoFromJson(this.getContext(), jsonMovieResponse);
            return movieData;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(Movie[] data) {
        movieData = data;
        super.deliverResult(data);
    }
}
