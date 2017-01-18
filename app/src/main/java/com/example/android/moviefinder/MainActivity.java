package com.example.android.moviefinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.utils.MovieDatabaseJsonUtils;
import com.example.android.moviefinder.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);
        mRecyclerView.setHasFixedSize(true);

        new MovieAsyncTask().execute(NetworkUtils.MOST_POPULAR);


    }

    @Override
    public void onClick(Movie selected) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TITLE, selected.getTitle());
        intent.putExtra(POSTER, selected.getPosterUrl());
        intent.putExtra(OVERVIEW, selected.getSynopsis());
        intent.putExtra(RELEASE_DATE, selected.getReleaseDate());
        intent.putExtra(USER_RATE, selected.getUserRate());
        startActivity(intent);
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String pref = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(pref);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                Movie[] movieData = MovieDatabaseJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this, jsonMovieResponse);

                return movieData;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] movieData) {
            if (movieData != null) {
                movieAdapter.setMovieArray(movieData);
            }
        }
    }

}
