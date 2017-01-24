package com.example.android.moviefinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.moviefinder.R;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private String[] trailerArray;
    private static final String TITLE_PREFIX = "Trailer ";
    private TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        public void onClick(String url);
    }


    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        String trailerTitle = TITLE_PREFIX + position;
        holder.mTrailerTitle.setText(trailerTitle);
    }

    @Override
    public int getItemCount() {
        if (trailerArray == null) {
            return 0;
        } else {
            return trailerArray.length;
        }
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton mPlayButton;
        private TextView mTrailerTitle;


        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            mPlayButton = (ImageButton) itemView.findViewById(R.id.ib_play_trailer);
            mTrailerTitle = (TextView) itemView.findViewById(R.id.tv_trailer_numb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(trailerArray[position]);
        }
    }

    public void setTrailerData(String[] trailers) {
        trailerArray = trailers;
        notifyDataSetChanged();
    }
}
