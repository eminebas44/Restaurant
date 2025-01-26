package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Indirim {
    private int id;
    private String amount;

    @JsonProperty("kullanici_id")
    private int kullaniciId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }
}
