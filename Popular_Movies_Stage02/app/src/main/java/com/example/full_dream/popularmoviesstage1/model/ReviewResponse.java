package com.example.full_dream.popularmoviesstage1.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Provides a data model to represent the initial JSON HTTP response as a POJO
 * that contains the desired Review details in the 'reviews' JSONArray.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 */
public class ReviewResponse {
    @Json(name = "id")
    private Integer id;
    @Json(name = "page")
    private Integer page;
    @Json(name = "reviews")
    private List<Review> reviews = null;
    @Json(name = "total_pages")
    private Integer totalPages;
    @Json(name = "total_reviews")
    private Integer totalReviews;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

}
