package com.example.android.moviefinder.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class MovieDatabaseJsonUtils {

    public static String[] getSimpleWeatherStringsFromJson(Context context, String moviesJsonStr)
            throws JSONException {

        final String TMDB_RESULTS = "results";

        final String TMDB_POSTER = "poster_path";

        final String TMDB_ERROR_MOVIES = "status_code";

        /* String array to hold each day's weather String */
        String[] posterArray = null;

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

        posterArray = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            String postalPath;

            JSONObject movie = movieArray.getJSONObject(i);

            postalPath = movie.getString(TMDB_POSTER);

            posterArray[i] = postalPath;
        }

        return posterArray;
    }
}