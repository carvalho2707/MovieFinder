package com.example.android.moviefinder.model;

/**
 * Created by tiago.carvalho on 01/18/17.
 */

public class Movie {

    private String posterUrl;
    private String title;
    private String synopsis;
    private String userRate;
    private String releaseDate;

    public Movie(String posterUrl, String title, String synopsis, String userRate, String releaseDate) {
        this.posterUrl = posterUrl;
        this.title = title;
        this.synopsis = synopsis;
        this.userRate = userRate;
        this.releaseDate = releaseDate;
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
}
