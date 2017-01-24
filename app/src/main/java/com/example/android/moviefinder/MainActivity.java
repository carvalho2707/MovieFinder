package com.example.android.moviefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviefinder.adapters.MovieAdapter;
import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.tasks.MovieAsyncTaskLoader;
import com.example.android.moviefinder.tasks.MovieAsyncTaskLoaderListener;
import com.example.android.moviefinder.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Movie[]>, MovieAsyncTaskLoaderListener {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";
    private static final String ID = "tmdb_id";

    private static final String SORT_KEY = "sort_key";
    private static final String FAVORITE_KEY = "favorite_key";

    private static final int MOVIE_LOADER_ID = 0;

    private String currentSort;
    private String currentFilterFavorites;

    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentFilterFavorites = "OFF";
        currentSort = NetworkUtils.MOST_POPULAR;

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SORT_KEY)) {
                currentSort = savedInstanceState.getString(SORT_KEY);
            }

            if (savedInstanceState.containsKey(FAVORITE_KEY)) {
                currentFilterFavorites = savedInstanceState.getString(FAVORITE_KEY);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        movieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);
        mRecyclerView.setHasFixedSize(true);

        int loaderId = MOVIE_LOADER_ID;

        LoaderManager.LoaderCallbacks<Movie[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

    }

    @Override
    public Loader<Movie[]> onCreateLoader(int id, Bundle args) {
        return new MovieAsyncTaskLoader(MainActivity.this, this, currentSort);
    }

    @Override
    public void onLoadFinished(Loader<Movie[]> loader, Movie[] data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data != null) {
            movieAdapter.setMovieArray(data);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movie[]> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

    @Override
    public void onClick(Movie selected) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TITLE, selected.getTitle());
        intent.putExtra(POSTER, selected.getPosterUrl());
        intent.putExtra(OVERVIEW, selected.getSynopsis());
        intent.putExtra(RELEASE_DATE, selected.getReleaseDate());
        intent.putExtra(USER_RATE, selected.getUserRate());
        intent.putExtra(ID, selected.getId());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_KEY, currentSort);
        outState.putString(FAVORITE_KEY, currentFilterFavorites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.filter_favorites);
        handleFavoriteIcon(item);
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
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
            return true;
        } else if (id == R.id.filter_favorites) {
            changeSortFavorites(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFavoriteIcon(MenuItem item) {
        if (currentFilterFavorites.equals("OFF")) {
            item.setIcon(android.R.drawable.star_off);
        } else {
            item.setIcon(android.R.drawable.star_on);
        }
    }

    private void changeSortFavorites(MenuItem item) {
        if (currentFilterFavorites.equals("OFF")) {
            item.setIcon(android.R.drawable.star_on);
            currentFilterFavorites = "ON";
        } else {
            item.setIcon(android.R.drawable.star_off);
            currentFilterFavorites = "OFF";
        }
    }

    private void changeSort(String newSort) {
        if (!currentSort.equals(newSort)) {
            currentSort = newSort;
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void beforeExecute() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }
}