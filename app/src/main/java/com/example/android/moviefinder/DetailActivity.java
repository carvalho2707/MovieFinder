package com.example.android.moviefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviefinder.R;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {
    private static final String TITLE = "title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String USER_RATE = "vote_average";
    private static final String POSTER = "poster_path";

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

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(TITLE)) {
                mMovieTitle.setText(intentThatStartedThisActivity.getStringExtra(TITLE));
            }

        }
    }
}
