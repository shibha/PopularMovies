package com.example.android.popularmoviesstage1.utilities;

import com.example.android.popularmoviesstage1.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonUtils to parse MOVIES DATA JSON string and create MOVIES LIST
 */
public class JsonUtils {

    public static List<Movie> createMovieList(String json) {
        JSONObject base = null;
        JSONArray results = null;
        List<Movie> movies = new ArrayList<>();
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
            movie.setTitle(movie_json.getString("title"));
            movie.setImgPath(movie_json.getString("imgPath"));
            movie.setPlotSynopsis(movie_json.getString("overview"));
            movie.setUserRating(movie_json.getInt("vote_average"));
            movie.setReleaseDate(movie_json.getString("release_date"));
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
