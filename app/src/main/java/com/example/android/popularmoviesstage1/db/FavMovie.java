package com.example.android.popularmoviesstage1.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "fav_movies")

public class FavMovie {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String plotSynopsis;
    private String userRating;
    private String databaseId;
    private String releaseDate;
    private String movieImg;
    private String coverImg;


    public FavMovie(int id, String title, String plotSynopsis, String releaseDate, String userRating, String movieImg, String coverImg, String databaseId) {
        this.id = id;
        this.title = title;
        this.plotSynopsis = plotSynopsis;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
        this.movieImg = movieImg;
        this.coverImg = coverImg;
        this.databaseId = databaseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
}
