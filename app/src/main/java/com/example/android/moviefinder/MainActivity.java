package com.example.android.moviefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

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

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap largeIcon3 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        movieAdapter.setMovieArray(new Bitmap[]{largeIcon, largeIcon2, largeIcon3});


    }

    @Override
    public void onClick(int position) {
        String toastMessage = "Item #" + position + " clicked.";
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

}
