package com.example.full_dream.popularmoviesstage1.model;

import com.squareup.moshi.Json;

/**
 * Provides a data model to represent a Trailer POJO to hold parsed JSON data.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 */
public class Trailer {
    @Json(name = "id")
    private String id;
    @Json(name = "iso_639_1")
    private String iso6391;
    @Json(name = "iso_3166_1")
    private String iso31661;
    @Json(name = "key")
    private String key;
    @Json(name = "name")
    private String name;
    @Json(name = "site")
    private String site;
    @Json(name = "size")
    private Integer size;
    @Json(name = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
