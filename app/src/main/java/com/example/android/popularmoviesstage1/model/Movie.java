package com.example.android.popularmoviesstage1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public Movie(){}

    private Movie(Parcel in) {
        this.title = in.readString();
        this.plotSynopsis = in.readString();
        this.releaseDate = in.readString();
        this.userRating = in.readInt();
        this.imgPath = in.readString();
        this.coverImg = in.readString();
        this.id = in.readInt();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public String getTitle() {
        return title;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public int getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    private String title;
    private String imgPath;

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

    private String plotSynopsis;
    private int userRating;
    private String releaseDate;
    private int id;
    private String coverImg;
    private String databaseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(plotSynopsis);
        parcel.writeString(releaseDate);
        parcel.writeInt(userRating);
        parcel.writeString(imgPath);
        parcel.writeString(coverImg);
        parcel.writeInt(id);
    }
}
