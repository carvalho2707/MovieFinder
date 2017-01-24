package com.example.android.moviefinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviefinder.R;
import com.example.android.moviefinder.model.Movie;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private String[] reviewsArray;

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutId, parent, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        String reviewText = reviewsArray[position];
        holder.mReviewText.setText(reviewText);
    }

    @Override
    public int getItemCount() {
        if (reviewsArray == null) {
            return 0;
        } else {
            return reviewsArray.length;
        }
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mReviewText;


        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mReviewText = (TextView) itemView.findViewById(R.id.tv_review_content);
        }
    }

    public void setReviewData(String[] reviews) {
        reviewsArray = reviews;
        notifyDataSetChanged();
    }

}
