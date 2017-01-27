package com.example.android.moviefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by tiago.carvalho on 01/18/17.
 */

public class Movie implements Parcelable {

    private int tmdbId;
    private long id;
    private String posterUrl;
    private String title;
    private String synopsis;
    private String userRate;
    private String releaseDate;
    private List<String> trailerList;
    private List<String> reviewList;

    public Movie() {

    }

    private Movie(Parcel in) {
        this.tmdbId = in.readInt();
        this.id = in.readLong();
        this.posterUrl = in.readString();
        this.title = in.readString();
        this.synopsis = in.readString();
        this.userRate = in.readString();
        this.releaseDate = in.readString();
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
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tmdbId);
        dest.writeLong(id);
        dest.writeString(posterUrl);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(userRate);
        dest.writeString(releaseDate);
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<String> trailerList) {
        this.trailerList = trailerList;
    }

    public List<String> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<String> reviewList) {
        this.reviewList = reviewList;
    }
}
