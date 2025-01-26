package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Garson {
    private int id;
    private String name;

    @JsonProperty("calisan_id")
    private int calisanId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }
}
