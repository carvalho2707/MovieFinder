package com.example.android.moviefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviefinder.R;
import com.example.android.moviefinder.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";
    private static final String TMDB_POSTER_NORMAL_SIZE = "w342";

    private TextView mMovieTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mSynopses;
    private TextView mUserRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        mPoster = (ImageView) findViewById(R.id.iv_movie_thumbnail);
        mReleaseDate = (TextView) findViewById(R.id.tv_movie_release);
        mSynopses = (TextView) findViewById(R.id.tv_synopsis);
        mUserRate = (TextView) findViewById(R.id.tv_movie_rate);

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
