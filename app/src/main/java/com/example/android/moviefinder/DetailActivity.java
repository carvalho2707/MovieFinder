package com.example.android.moviefinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.android.moviefinder.adapters.MovieAdapter;
import com.example.android.moviefinder.adapters.ReviewAdapter;
import com.example.android.moviefinder.adapters.TrailerAdapter;
import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.tasks.MovieAsyncTaskLoader;
import com.example.android.moviefinder.tasks.ReviewsAsyncTaskLoader;
import com.example.android.moviefinder.tasks.TrailerAsyncTaskLoader;
import com.example.android.moviefinder.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String[]>, TrailerAdapter.TrailerAdapterOnClickHandler {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";
    private static final String TMDB_POSTER_NORMAL_SIZE = "w342";
    private static final String ID = "tmdb_id";

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

    private RecyclerView mRecyclerViewReviews;
    private ReviewAdapter mReviewsAdapter;
    private RecyclerView mRecyclerViewTrailers;
    private TrailerAdapter mTrailerAdapter;
    private static final int REVIEWS_LOADER_ID = 1;
    private static final int TRAILER_LOADER_ID = 2;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(TITLE)) {
                mMovieTitle.setText(intentThatStartedThisActivity.getStringExtra(TITLE));
            }
            if (intentThatStartedThisActivity.hasExtra(OVERVIEW)) {
                mSynopses.setText(intentThatStartedThisActivity.getStringExtra(OVERVIEW));
            }
            if (intentThatStartedThisActivity.hasExtra(RELEASE_DATE)) {
                String releaseDate = intentThatStartedThisActivity.getStringExtra(RELEASE_DATE);
                String year = releaseDate.substring(0, 4);
                mReleaseDate.setText(year);
            }
            if (intentThatStartedThisActivity.hasExtra(USER_RATE)) {
                String userRate = intentThatStartedThisActivity.getStringExtra(USER_RATE) + "/10";
                mUserRate.setText(userRate);
            }
            if (intentThatStartedThisActivity.hasExtra(POSTER)) {
                String url = NetworkUtils.IMAGE_URL + TMDB_POSTER_NORMAL_SIZE + intentThatStartedThisActivity.getStringExtra(POSTER);
                Picasso.with(this).load(url).into(mPoster);
            }
            if (intentThatStartedThisActivity.hasExtra(ID)) {
                movieId = intentThatStartedThisActivity.getIntExtra(ID, 0);
            }

        }

        mToggleFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mToggleFavorite.setTextColor(ContextCompat.getColor(buttonView.getContext(), R.color.colorBlueSoft));
                } else {
                    mToggleFavorite.setTextColor(ContextCompat.getColor(buttonView.getContext(), android.R.color.darker_gray));
                }
            }
        });

        mRecyclerViewReviews = (RecyclerView) findViewById(R.id.rv_reviews);

        LinearLayoutManager layoutReviewManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutReviewManager);

        mReviewsAdapter = new ReviewAdapter();
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);
        mRecyclerViewReviews.setHasFixedSize(true);

        mRecyclerViewTrailers = (RecyclerView) findViewById(R.id.rv_reviews);

        LinearLayoutManager layoutTrailerManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTrailers.setLayoutManager(layoutTrailerManager);

        mTrailerAdapter = new TrailerAdapter();
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);
        mRecyclerViewTrailers.setHasFixedSize(true);


        int reviewsLoaderId = REVIEWS_LOADER_ID;
        int trailerLoaderId = TRAILER_LOADER_ID;
        LoaderManager.LoaderCallbacks<String[]> callback = DetailActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(reviewsLoaderId, bundleForLoader, callback);
        getSupportLoaderManager().initLoader(trailerLoaderId, bundleForLoader, callback);

    }

    @Override
    public Loader<String[]> onCreateLoader(int id, Bundle args) {
        if (id == REVIEWS_LOADER_ID) {
            return new ReviewsAsyncTaskLoader(DetailActivity.this, movieId);
        } else if (id == TRAILER_LOADER_ID) {
            return new TrailerAsyncTaskLoader(DetailActivity.this, movieId);
        } else {
            return null;
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
        }

    }

    @Override
    public void onLoaderReset(Loader<String[]> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }

    @Override
    public void onClick(String id) {
        String videoId = id;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }
}
