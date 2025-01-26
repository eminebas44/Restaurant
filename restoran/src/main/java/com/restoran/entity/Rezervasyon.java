package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rezervasyon {
    private int id;
    private String tarih;
    private String saat;

    @JsonProperty("user_id")
    private int userId;

    @JsonProperty("masa_id")
    private int masaId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMasaId() {
        return masaId;
    }

    public void setMasaId(int masaId) {
        this.masaId = masaId;
    }
}
