package com.example.android.moviefinder.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by tiago.carvalho on 01/23/17.
 */
public class MovieFinderContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.moviefinder";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITES = "favorites";

    public static final class MovieFinderEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_TMDBID = "tmdb_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_USER_RATE = "vote_average";
        public static final String COLUMN_POSTER = "poster_path";

        public static Uri buildMovieUriWithTmdbID(int id) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(id))
                    .build();
        }

    }
}
