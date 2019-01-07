package com.example.android.popularmoviesstage1.utilities;

import android.app.Activity;

import com.example.android.popularmoviesstage1.MovieActivity;
import com.example.android.popularmoviesstage1.model.Movie;
import com.example.android.popularmoviesstage1.model.Review;
import com.example.android.popularmoviesstage1.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonUtils to parse MOVIES DATA JSON string and create MOVIES LIST
 */
public class JsonUtils {

    private static final String imgBasePath ="http://image.tmdb.org/t/p/w185//";

    public static List<Movie> createMovieList(String json) {
        JSONObject base = null;
        JSONArray results = null;
        List<Movie> movies = new ArrayList<>();
        if(json == null) {
            return movies;
        }
        /**
         * Read the  JSON string
         */
        try {
            base = new JSONObject(json);
            results = base.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                movies.add(parseMovieJSON(results.getJSONObject(i)));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return movies;
    }

    private static Movie parseMovieJSON(JSONObject movie_json) {
        Movie movie = new Movie();

        try {
            movie.setId(movie_json.getInt("id"));
            movie.setTitle(movie_json.getString("title"));
            movie.setImgPath(imgBasePath + movie_json.getString("poster_path"));
            movie.setPlotSynopsis(movie_json.getString("overview"));
            movie.setUserRating(movie_json.getInt("vote_average"));
            movie.setReleaseDate(movie_json.getString("release_date"));
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Trailer> createMovieTrailers(String trailersJson) {
        JSONObject base = null;
        JSONArray results = null;
        List<Trailer> trailers = new ArrayList<>();
        if(trailersJson == null) {
            return trailers;
        }
        /**
         * Read the  JSON string
         */
        try {
            base = new JSONObject(trailersJson);
            results = base.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                trailers.add(parseMovieTrailersJSON(results.getJSONObject(i)));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return trailers;
    }

    private static Trailer parseMovieTrailersJSON(JSONObject jsonObject) {
        Trailer trailer = new Trailer();

        try {
            trailer.setId(jsonObject.getString("id"));
            trailer.setIso_639(jsonObject.getString("iso_639_1"));
            trailer.setIso_3166(jsonObject.getString("iso_3166_1"));
            trailer.setName(jsonObject.getString("name"));
            trailer.setKey(jsonObject.getString("key"));
            trailer.setSite(jsonObject.getString("site"));
            trailer.setType(jsonObject.getString("type"));
            trailer.setSize(jsonObject.getInt("size"));
            return trailer;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Review parseMovieReviewsJSON(JSONObject jsonObject) {
        Review review = new Review();

        try {
            review.setAuthor(jsonObject.getString("author"));
            review.setContent(jsonObject.getString("content"));
            return review;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static List<Review> createMovieReviews(String reviewsJsonString) {
        JSONObject base = null;
        JSONArray results = null;
        List<Review> reviews = new ArrayList<>();
        if(reviewsJsonString == null) {
            return reviews;
        }
        /**
         * Read the  JSON string
         */
        try {
            base = new JSONObject(reviewsJsonString);
            results = base.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                reviews.add(parseMovieReviewsJSON(results.getJSONObject(i)));
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return reviews;
    }
}
