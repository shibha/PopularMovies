package com.example.android.popularmoviesstage1.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.popularmoviesstage1.MovieDetailActivity;
import com.example.android.popularmoviesstage1.R;
import com.example.android.popularmoviesstage1.model.Review;
import java.util.List;

public class MovieReviewRecycleViewAdaptor extends RecyclerView.Adapter<MovieReviewRecycleViewAdaptor.ReviewAdapterViewHolder> {
    private List<Review> reviews;

    public MovieReviewRecycleViewAdaptor(MovieDetailActivity detailActivity) {
    }


    @Override
    public MovieReviewRecycleViewAdaptor.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new MovieReviewRecycleViewAdaptor.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        reviewAdapterViewHolder.reviewAuthor.setText(reviews.get(position).getAuthor());
        reviewAdapterViewHolder.reviewContext.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView reviewAuthor;
        public final TextView reviewContext;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.author);
            reviewContext = itemView.findViewById(R.id.content);
        }
    }

    public void setReviewData(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}