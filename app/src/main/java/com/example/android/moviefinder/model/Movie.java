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

    public Movie(int tmdbId, String posterUrl, String title, String synopsis, String userRate, String releaseDate) {
        this.tmdbId = tmdbId;
        this.posterUrl = posterUrl;
        this.title = title;
        this.synopsis = synopsis;
        this.userRate = userRate;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        tmdbId = in.readInt();
        id = in.readLong();
        posterUrl = in.readString();
        title = in.readString();
        synopsis = in.readString();
        userRate = in.readString();
        releaseDate = in.readString();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tmdbId);
        dest.writeString(posterUrl);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(userRate);
        dest.writeString(releaseDate);
        dest.writeLong(id);
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
}
