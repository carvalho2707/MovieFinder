package com.example.android.moviefinder.tasks;

import com.example.android.moviefinder.model.Movie;

/**
 * Created by tiago.carvalho on 01/19/17.
 */

public interface MovieAsyncTaskListener {
    void beforeExecute();

    void afterExecute(Movie[] movieData);

}
