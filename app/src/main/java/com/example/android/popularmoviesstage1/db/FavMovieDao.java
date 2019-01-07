package com.example.android.popularmoviesstage1.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;


@Dao
public interface FavMovieDao {


    @Query("SELECT * FROM fav_movies ORDER BY id")
    LiveData<List<FavMovie>> loadFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavMovie favMovie);

    @Query("DELETE FROM fav_movies WHERE databaseId = :id")
    void deleteMovieMy(String id);

    @Query("SELECT * FROM fav_movies WHERE databaseId = :id")
    FavMovie getFavMoviesBy(String id);
}
