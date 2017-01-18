package com.example.android.moviefinder.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.android.moviefinder.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class MovieDatabaseJsonUtils {
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER = "poster_path";
    private static final String TMDB_ERROR_MOVIES = "status_code";
    private static final String TMDB_POSTER_NORMAL_SIZE = "w500";
    private static final String TMDB_TITLE = "title";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_RELEASE_DATE = "release_date";
    private static final String TMDB_USER_RATE = "vote_average";

    public static Movie[] getSimpleWeatherStringsFromJson(Context context, String moviesJsonStr)
            throws JSONException {



        /* String array to hold each day's weather String */
        Movie[] results = null;

        JSONObject movieObj = new JSONObject(moviesJsonStr);

        /* Is there an error? */
        if (movieObj.has(TMDB_ERROR_MOVIES)) {
            int errorCode = movieObj.getInt(TMDB_ERROR_MOVIES);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case 34:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = movieObj.getJSONArray(TMDB_RESULTS);

        results = new Movie[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String posterPath;
            String title;
            String synopsis;
            String userRate;
            String releaseDate;

            JSONObject movie = movieArray.getJSONObject(i);

            posterPath = NetworkUtils.IMAGE_URL + TMDB_POSTER_NORMAL_SIZE + movie.getString(TMDB_POSTER);
            title = movie.getString(TMDB_TITLE);
            synopsis = movie.getString(TMDB_OVERVIEW);
            userRate = movie.getString(TMDB_USER_RATE);
            releaseDate = movie.getString(TMDB_RELEASE_DATE);

            Movie tmp = new Movie(posterPath, title, synopsis, userRate, releaseDate);
            results[i] = tmp;
        }

        return results;
    }
}