package com.example.android.moviefinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.moviefinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private String[] trailerArray;
    private static final String TITLE_PREFIX = "Trailer ";
    private TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(String url);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler handler) {
        mClickHandler = handler;
    }


    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
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
        @BindView(R.id.tv_trailer_numb)
        private ImageButton mPlayButton;
        @BindView(R.id.ib_play_trailer)
        private TextView mTrailerTitle;


        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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

    public String[] getTrailerData() {
        return trailerArray;
    }
}
