package com.example.android.moviefinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.moviefinder.model.Movie;
import com.example.android.moviefinder.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by tiago.carvalho on 01/17/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TMDB_POSTER_NORMAL_SIZE = "w500";
    private final MovieAdapterOnClickHandler mClickHandler;
    private Movie[] movieArray;

    public MovieAdapter(MovieAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie selected);

    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private ImageView mMoviePoster;


        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(movieArray[position]);
        }
    }

    @Override
    public int getItemCount() {
        if (movieArray == null) {
            return 0;
        }
        return movieArray.length;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder viewHolder, int position) {
        Picasso.with(viewHolder.mMoviePoster.getContext()).load(NetworkUtils.IMAGE_URL + TMDB_POSTER_NORMAL_SIZE + movieArray[position].getPosterUrl()).into(viewHolder.mMoviePoster);
    }

    public void setMovieArray(Movie[] movieData) {
        movieArray = movieData;
        notifyDataSetChanged();
    }
}
