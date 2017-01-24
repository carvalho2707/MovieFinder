package com.example.android.moviefinder.utils;

import android.content.Context;

import com.example.android.moviefinder.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public final class TheMovieDatabaseJsonUtils {
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER = "poster_path";
    private static final String TMDB_ERROR_MOVIES = "status_code";
    private static final String TMDB_POSTER_NORMAL_SIZE = "w500";
    private static final String TMDB_TITLE = "title";
    private static final String TMDB_OVERVIEW = "overview";
    private static final String TMDB_ID = "id";
    private static final String TMDB_RELEASE_DATE = "release_date";
    private static final String TMDB_USER_RATE = "vote_average";
    private static final String TMDB_VIDEO_KEY = "key";
    private static final String TMDB_TRAILER_TYPE = "type";
    private static final String TMDB_TRAILER_TYPE_TRAILER = "Trailer";
    private static final String TMDB_TRAILER_SITE = "site";
    private static final String TMDB_TRAILER_SITE_YOUTUBE = "site";
    private static final String TMDB_REVIEW_CONTENT = "content";

    public static Movie[] getMovieInfoFromJson(Context context, String moviesJsonStr)
            throws JSONException {

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
            int id;

            JSONObject movie = movieArray.getJSONObject(i);

            posterPath = movie.getString(TMDB_POSTER);
            title = movie.getString(TMDB_TITLE);
            synopsis = movie.getString(TMDB_OVERVIEW);
            userRate = movie.getString(TMDB_USER_RATE);
            releaseDate = movie.getString(TMDB_RELEASE_DATE);
            id = movie.getInt(TMDB_ID);

            Movie tmp = new Movie(id, posterPath, title, synopsis, userRate, releaseDate);
            results[i] = tmp;
        }

        return results;
    }

    public static String[] getTrailersFromJson(String movieTrailerResponse) throws JSONException {
        String[] results = null;
        JSONObject movieObj = new JSONObject(movieTrailerResponse);
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

        JSONArray trailersArray = movieObj.getJSONArray(TMDB_RESULTS);
        results = new String[trailersArray.length()];

        for (int i = 0; i < trailersArray.length(); i++) {
            JSONObject obj = trailersArray.getJSONObject(i);
            String site = obj.getString(TMDB_TRAILER_SITE);
            String type = obj.getString(TMDB_TRAILER_TYPE);
            if (site.equals(TMDB_TRAILER_SITE_YOUTUBE) && type.equals(TMDB_TRAILER_TYPE_TRAILER)) {
                String path = obj.getString(TMDB_VIDEO_KEY);
                results[i] = path;
            }
        }
        return results;
    }

    public static String[] getReviewsFromJson(String movieReviewResponse) throws JSONException {
        String[] results = null;
        JSONObject movieObj = new JSONObject(movieReviewResponse);
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

        JSONArray trailersArray = movieObj.getJSONArray(TMDB_RESULTS);
        results = new String[trailersArray.length()];

        for (int i = 0; i < trailersArray.length(); i++) {
            JSONObject obj = trailersArray.getJSONObject(i);
            String content = obj.getString(TMDB_REVIEW_CONTENT);
            results[i] = content;
        }
        return results;
    }
}