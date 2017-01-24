package com.example.android.moviefinder.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the MovieDB servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String AUTHORITY_BASE = "api.themoviedb.org";
    private static final String SCHEME_BASE = "http";
    private static final String MOVIE_PATH = "movie";

    public static final String MOST_POPULAR = "movie/popular";
    public static final String TOP_RATED = "movie/top_rated";

    public static final String SEARCH_REVIEWS = "reviews";
    public static final String SEARCH_TRAILERS = "videos";

    private static final String API_PARAM = "api_key";
    private static final String API_KEY = "aff4937b06de5edce12992247f3068c3";

    public static URL buildUrlTopRated(String preference) {
        return buildUrl(TOP_RATED);
    }

    public static URL buildUrlMostPop(String preference) {
        return buildUrl(MOST_POPULAR);
    }

    public static URL buildUrl(String preference) {
        Uri builtUri = Uri.parse(BASE_URL + preference).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "buildUrl " + url);

        return url;
    }

    public static URL buildUrlFetchReviewsById(int id) {
        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(SCHEME_BASE)
                .authority(AUTHORITY_BASE)
                .appendPath("3")
                .appendPath(MOVIE_PATH)
                .appendPath(String.valueOf(id))
                .appendPath(SEARCH_REVIEWS)
                .appendQueryParameter(API_PARAM, API_KEY).build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        Log.v(TAG, "buildUrlFetchReviewsById " + url);
        return url;
    }

    public static URL buildUrlFetchTrailersById(int id) {
        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder.scheme(SCHEME_BASE)
                .authority(AUTHORITY_BASE)
                .appendPath("3")
                .appendPath(MOVIE_PATH)
                .appendPath(String.valueOf(id))
                .appendPath(SEARCH_TRAILERS)
                .appendQueryParameter(API_PARAM, API_KEY).build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        Log.v(TAG, "buildUrlFetchReviewsById " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}