package com.example.full_dream.popularmoviesstage1.model;

/**
 * Provides a data model to represent a Movie object to hold parsed JSON data.
 */

public class Movie {
    private String title;
    private String releaseDate;
    private int poster; // drawable reference id
    private float voteAvg;
    private String plotSynopsis;

    public Movie(String title, String releaseDate, int poster, float voteAvg, String plotSynopsis){
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public float getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(float voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }
}
