package com.example.android.moviefinder.data;

import android.provider.BaseColumns;

/**
 * Created by tiago.carvalho on 01/23/17.
 */
public class MovieFinderContract {

    public static final class MovieFinderEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_MOVIE_TITLE = "title";

    }
}
