package com.example.full_dream.popularmoviesstage1.model;

import com.squareup.moshi.Json;

/**
 * Provides a data model to represent a Review POJO to hold parsed JSON data.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 */
public class Review {
    @Json(name = "author")
    private String author;
    @Json(name = "content")
    private String content;
    @Json(name = "id")
    private String id;
    @Json(name = "url")
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
