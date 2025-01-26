package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Teslimat {
    private int id;
    private String address;
    private String durum;

    @JsonProperty("restaurant_id")
    private int restaurantId;

    @JsonProperty("siparis_id")
    private int siparisId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(int siparisId) {
        this.siparisId = siparisId;
    }
}
