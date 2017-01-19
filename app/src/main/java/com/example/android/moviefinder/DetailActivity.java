package com.example.android.moviefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviefinder.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";
    private static final String TMDB_POSTER_NORMAL_SIZE = "w342";

   C(R.id.tv_movie_title)
    TextView mMovieTitle;
    @BindView(R.id.iv_movie_thumbnail)
    ImageView mPoster;
    @BindView(R.id.tv_movie_release)
    TextView mReleaseDate;
    @BindView(R.id.tv_synopsis)
    TextView mSynopses;
    @BindView(R.id.tv_movie_rate)
    TextView mUserRate;

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

        }
    }
}
