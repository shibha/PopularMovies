package com.example.android.popularmoviesstage1.model;

import java.io.Serializable;

public class Trailer implements Serializable {
    private String id;
    private String iso_639;

    public String getIso_3166() {
        return iso_3166;
    }

    public void setIso_3166(String iso_3166) {
        this.iso_3166 = iso_3166;
    }

    private String iso_3166;
    private String key;
    private String name;
    private String site;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso_639() {
        return iso_639;
    }

    public void setIso_639(String iso_639) {
        this.iso_639 = iso_639;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int size;
    private String type;
}
