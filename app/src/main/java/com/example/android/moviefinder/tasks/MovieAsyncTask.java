package com.example.android.moviefinder.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.utils.MovieDatabaseJsonUtils;
import com.example.android.moviefinder.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]> {
    private Context context;
    private MovieAsyncTaskListener listener;

    public MovieAsyncTask(Context context, MovieAsyncTaskListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Movie[] doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        String pref = params[0];
        URL movieRequestUrl = NetworkUtils.buildUrl(pref);

        if (NetworkUtils.isOnline(context)) {
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                Movie[] movieData = MovieDatabaseJsonUtils.getSimpleWeatherStringsFromJson(context, jsonMovieResponse);

                return movieData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Movie[] movieData) {
        listener.afterExecute(movieData);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.beforeExecute();
    }
}
