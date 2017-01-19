package com.example.android.moviefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.tasks.MovieAsyncTask;
import com.example.android.moviefinder.tasks.MovieAsyncTaskListener;
import com.example.android.moviefinder.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, MovieAsyncTaskListener {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";
    private String currentSort;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentSort = NetworkUtils.MOST_POPULAR;

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadMovieData();
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

    @Override
    public void beforeExecute() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterExecute(Movie[] movieData) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movieData != null) {
            movieAdapter.setMovieArray(movieData);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sortby_popular) {
            changeSort(NetworkUtils.MOST_POPULAR);
            return true;
        } else if (id == R.id.sortby_toprated) {
            changeSort(NetworkUtils.TOP_RATED);
            return true;
        } else if (id == R.id.action_refresh) {
            movieAdapter.setMovieArray(null);
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeSort(String newSort) {
        if (!currentSort.equals(newSort)) {
            currentSort = newSort;
            new MovieAsyncTask(MainActivity.this, this).execute(currentSort);
        }
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void loadMovieData() {
        showMovieDataView();
        new MovieAsyncTask(MainActivity.this, this).execute(currentSort);
    }

}
