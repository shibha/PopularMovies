package com.example.android.popularmoviesstage1.model;

import java.io.Serializable;
import java.util.List;

public class TrailerAndReviews implements Serializable {
    List<Trailer> trailers;
    List<Review> reviews;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
