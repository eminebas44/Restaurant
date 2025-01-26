package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vardiya {
    private int id;
    private String saatler;

    @JsonProperty("calisan_id")
    private int calisanId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaatler() {
        return saatler;
    }

    public void setSaatler(String saatler) {
        this.saatler = saatler;
    }

    public int getCalisanId() {
        return calisanId;
    }

    public void setCalisanId(int calisanId) {
        this.calisanId = calisanId;
    }
}
