package com.example.full_dream.popularmoviesstage1.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Provides a data model to represent the initial JSON HTTP response as a POJO
 * that contains the desired Trailer details in the 'results' JSONArray.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 */
public class TrailerResponse {
    @Json(name = "id")
    private Integer id;
    @Json(name = "results")
    private List<Trailer> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
