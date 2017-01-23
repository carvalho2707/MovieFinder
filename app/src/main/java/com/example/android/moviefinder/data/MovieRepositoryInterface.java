package com.example.android.moviefinder.data;

import com.example.android.moviefinder.model.Movie;

import java.util.List;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public interface MovieRepositoryInterface {
    public void insertMovie(Movie movie);

    public void updateMovie(Movie movie);

    public void deleteMovie(Movie movie);

    List<Movie> getAllMovies(Movie movie);
}
