package com.example.android.popularmoviesstage1.model;

public class Movie {
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = "http://image.tmdb.org/t/p/w185//" + imgPath;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private String title;
    private String imgPath;
    private String plotSynopsis;
    private int userRating;
    private String releaseDate;
}
