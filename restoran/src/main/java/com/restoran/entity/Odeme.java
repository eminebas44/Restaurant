package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Odeme {
    private int id;
    private String tarih;
    private float amount;

    @JsonProperty("siparis_id")
    private int siparisId;

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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(int siparisId) {
        this.siparisId = siparisId;
    }
}
