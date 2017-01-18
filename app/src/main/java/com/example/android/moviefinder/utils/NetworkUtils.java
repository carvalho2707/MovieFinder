package com.example.android.moviefinder.utils;

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

    public static final String MOST_POPULAR = "movie/popular";
    public static final String TOP_RATED = "movie/top_rated";

    private static final String API_PARAM = "api_key";

    public static URL buildUrlTopRated(String preference) {
        return buildUrl(TOP_RATED);
    }

    public static URL buildUrlMostPop(String preference) {
        return buildUrl(MOST_POPULAR);
    }

    public static URL buildUrl(String preference) {
        Uri builtUri = Uri.parse(BASE_URL + preference).buildUpon()
                .appendQueryParameter(API_PARAM,"aff4937b06de5edce12992247f3068c3")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

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
}