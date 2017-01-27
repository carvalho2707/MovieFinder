package com.example.android.moviefinder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviefinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tiago.carvalho on 01/23/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private String[] reviewTextArray;

    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        String reviewText = reviewTextArray[position];
        holder.mReviewText.setText(reviewText);
    }

    @Override
    public int getItemCount() {
        if (reviewTextArray == null) {
            return 0;
        } else {
            return reviewTextArray.length;
        }
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_review_content)
        TextView mReviewText;


        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setReviewData(String[] reviews) {
        reviewTextArray = reviews;
        notifyDataSetChanged();
    }

}
