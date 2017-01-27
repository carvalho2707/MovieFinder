package com.example.android.moviefinder;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.moviefinder.adapters.ReviewAdapter;
import com.example.android.moviefinder.adapters.TrailerAdapter;
import com.example.android.moviefinder.data.MovieFinderContract;
import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.tasks.FavoritesByTmdbIdAsyncTaskLoader;
import com.example.android.moviefinder.tasks.ReviewsAsyncTaskLoader;
import com.example.android.moviefinder.tasks.TrailerAsyncTaskLoader;
import com.example.android.moviefinder.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]>, TrailerAdapter.TrailerAdapterOnClickHandler {
    @BindView(R.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.iv_movie_thumbnail)
    ImageView mPoster;
    @BindView(R.id.tv_movie_release)
    TextView mReleaseDate;
    @BindView(R.id.tv_synopsis)
    TextView mSynopses;
    @BindView(R.id.tv_movie_rate)
    TextView mUserRate;
    @BindView(R.id.tb_mark_favorite)
    ToggleButton mToggleFavorite;
    @BindView(R.id.tv_trailers_title)
    TextView mTrailersTitle;
    @BindView(R.id.tv_reviews_title)
    TextView mReviewsTitle;
    @BindView(R.id.rv_reviews)
    private RecyclerView mRecyclerViewReviews;
    @BindView(R.id.rv_trailers)
    private RecyclerView mRecyclerViewTrailers;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewsAdapter;

    private static final int REVIEWS_LOADER_ID = 1;
    private static final int TRAILER_LOADER_ID = 2;
    private static final int FAV_LOADER_ID = 3;
    private static final String TMDB_POSTER_NORMAL_SIZE = "w342";

    private int mMovieId;
    private long mMovieBaseId;
    private boolean controlToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            Movie movie;
            if (intentThatStartedThisActivity.hasExtra("Movies")) {
                movie = intentThatStartedThisActivity.getParcelableExtra("Movies");
                mMovieTitle.setText(movie.getTitle());
                mSynopses.setText(movie.getSynopsis());
                mReleaseDate.setText(movie.getReleaseDate().substring(0, 4));
                mUserRate.setText(movie.getUserRate() + "/10");
                String url = NetworkUtils.IMAGE_URL + TMDB_POSTER_NORMAL_SIZE + movie.getPosterUrl();
                mPoster.setTag(movie.getPosterUrl());
                Picasso.with(this).load(url).into(mPoster);
                mMovieId = movie.getTmdbId();
            }
        }

        LinearLayoutManager layoutReviewManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutReviewManager);

        mReviewsAdapter = new ReviewAdapter();
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);
        mRecyclerViewReviews.setHasFixedSize(true);

        LinearLayoutManager layoutTrailerManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTrailers.setLayoutManager(layoutTrailerManager);

        mTrailerAdapter = new TrailerAdapter(this);
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);
        mRecyclerViewTrailers.setHasFixedSize(true);

        LoaderManager.LoaderCallbacks<String[]> callback = DetailActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(REVIEWS_LOADER_ID, bundleForLoader, callback);
        getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, bundleForLoader, callback);
        getSupportLoaderManager().initLoader(FAV_LOADER_ID, bundleForLoader, callback);

        mToggleFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!controlToggle) {
                    if (isChecked) {
                        mToggleFavorite.setTextColor(ContextCompat.getColor(buttonView.getContext(), R.color.colorBlueSoft));

                        ContentValues values = new ContentValues();
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_TMDBID, mMovieId);
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_OVERVIEW, mSynopses.getText().toString());
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_POSTER, (String) mPoster.getTag());
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_RELEASE_DATE, mReleaseDate.getText().toString());
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_TITLE, mMovieTitle.getText().toString());
                        values.put(MovieFinderContract.MovieFinderEntry.COLUMN_USER_RATE, (String) mUserRate.getTag());


                        Uri uri = getContentResolver().insert(MovieFinderContract.MovieFinderEntry.CONTENT_URI, values);
                        String id = uri.getLastPathSegment();
                        mMovieBaseId = Long.valueOf(id);

                        if (uri != null) {
                            String textSuccess = getString(R.string.save_success);
                            Toast.makeText(getBaseContext(), textSuccess, Toast.LENGTH_LONG).show();
                        } else {
                            String textSuccess = getString(R.string.save_no_success);
                            Toast.makeText(getBaseContext(), textSuccess, Toast.LENGTH_LONG).show();
                            mToggleFavorite.setChecked(false);
                            mToggleFavorite.setTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.darker_gray));
                        }
                    } else {
                        mToggleFavorite.setTextColor(ContextCompat.getColor(buttonView.getContext(), android.R.color.darker_gray));

                        Uri uri = MovieFinderContract.MovieFinderEntry.CONTENT_URI.buildUpon().appendPath(Long.toString(mMovieBaseId)).build();
                        getContentResolver().delete(uri, null, null);
                        getSupportLoaderManager().restartLoader(FAV_LOADER_ID, null, DetailActivity.this);
                    }
                }
            }
        });
    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        if (id == REVIEWS_LOADER_ID) {
            return new ReviewsAsyncTaskLoader(DetailActivity.this, mMovieId);
        } else if (id == TRAILER_LOADER_ID) {
            return new TrailerAsyncTaskLoader(DetailActivity.this, mMovieId);
        } else if (id == FAV_LOADER_ID) {
            return new FavoritesByTmdbIdAsyncTaskLoader(DetailActivity.this, mMovieId);
        } else {
            throw new UnsupportedOperationException(getString(R.string.not_supported_operation));
        }
    }

    @Override
    public void onLoadFinished(Loader<String[]> loader, String[] data) {
        int loaderId = loader.getId();
        switch (loaderId) {
            case REVIEWS_LOADER_ID:
                if (data != null) {
                    mReviewsTitle.setVisibility(View.VISIBLE);
                    mReviewsAdapter.setReviewData(data);
                } else {
                    mReviewsTitle.setVisibility(View.INVISIBLE);
                }
                break;
            case TRAILER_LOADER_ID:
                if (data != null) {
                    mTrailersTitle.setVisibility(View.VISIBLE);
                    mTrailerAdapter.setTrailerData(data);
                } else {
                    mTrailersTitle.setVisibility(View.INVISIBLE);
                }
                break;
            case FAV_LOADER_ID:
                controlToggle = true;
                if (data != null && data.length > 0) {
                    mMovieBaseId = Long.valueOf(data[0]);
                    mToggleFavorite.setChecked(true);
                    mToggleFavorite.setTextColor(ContextCompat.getColor(this, R.color.colorBlueSoft));
                } else {
                    mToggleFavorite.setChecked(false);
                    mToggleFavorite.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                }
                controlToggle = false;
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
    }

    @Override
    public void onClick(String id) {
        String youtubePackageName = "com.google.android.youtube";
        if (isAppInstalled(youtubePackageName)) {
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            youtubeIntent.putExtra("VIDEO_ID", id);
            startActivity(youtubeIntent);
        } else {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(webIntent);
        }
    }

    private Intent createShareForecastIntent() {
        String[] trailers = mTrailerAdapter.getTrailerData();
        trailers = null;
        if (null != trailers && trailers.length > 0) {
            String trailerSuffix = trailers[0];
            String urlToShare = "https://www.youtube.com/watch?v=" + trailerSuffix;
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(urlToShare)
                    .getIntent();
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            return shareIntent;
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            if (shareIntent != null) {
                startActivity(shareIntent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        } else {
            return false;
        }
    }
}
