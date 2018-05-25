package com.example.full_dream.popularmoviesstage1.model;

import com.squareup.moshi.Json;

/**
 * Provides a data model to represent a Trailer POJO to hold parsed JSON data.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 */
public class Trailer {
    // I have no idea what this is for and there is no documentation for it that I could find
    @Json(name = "id")
    private String id;
    // Identifies the international language code (i.e. en, jp, kr, etc.)
    @Json(name = "iso_639_1")
    private String iso6391;
    // Identifies the country of origin (i.e. US, JP, KR, etc.)
    @Json(name = "iso_3166_1")
    private String iso31661;
    // Identifies the video key for YouTube (i.e. https://www.youtube.com/watch?v=<<key>>)
    @Json(name = "key")
    private String key;
    // Identifies the name of the video on YouTube
    @Json(name = "name")
    private String name;
    // It is always YouTube
    // Source: Moderator Travis Bell
    // https://www.themoviedb.org/talk/5451ec02c3a3680245005e3c?page=2
    @Json(name = "site")
    private String site;
    // Available video sizes (i.e. 360, 480, 720, and 1080)
    @Json(name = "size")
    private Integer size;
    // Type of video (i.e. trailer, clips, teasers, and featurettes)
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
