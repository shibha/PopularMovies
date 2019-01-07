package com.example.android.popularmoviesstage1.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.android.popularmoviesstage1.db.FavMovie;
import com.example.android.popularmoviesstage1.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public static List<Movie> convertList(List<FavMovie> favMovies){
        List<Movie> movies = new ArrayList<>();
        for(FavMovie favMovie: favMovies){
            movies.add(convert(favMovie));
        }
        return movies;
    }



    public static Movie convert(FavMovie currentMovie) {
        Movie movie = new Movie();
        movie.setId(currentMovie.getId());
        movie.setTitle(currentMovie.getTitle());
        movie.setCoverImg(currentMovie.getCoverImg());
        movie.setDatabaseId(currentMovie.getDatabaseId());
        movie.setImgPath(currentMovie.getMovieImg());
        movie.setUserRating(Integer.valueOf(currentMovie.getUserRating()));
        movie.setReleaseDate(currentMovie.getReleaseDate());
        movie.setPlotSynopsis(currentMovie.getPlotSynopsis());
        return movie;
    }
}
