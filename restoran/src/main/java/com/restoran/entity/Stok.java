package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stok {
    private int id;
    private int miktar;

    @JsonProperty("yemek_id")
    private int yemekId;

    @JsonProperty("icecek_id")
    private int icecekId;

    @JsonProperty("tatli_id")
    private int tatliId;

    @JsonProperty("tedarikci_id")
    private int tedarikciId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public int getYemekId() {
        return yemekId;
    }

    public void setYemekId(int yemekId) {
        this.yemekId = yemekId;
    }

    public int getIcecekId() {
        return icecekId;
    }

    public void setIcecekId(int icecekId) {
        this.icecekId = icecekId;
    }

    public int getTatliId() {
        return tatliId;
    }

    public void setTatliId(int tatliId) {
        this.tatliId = tatliId;
    }

    public int getTedarikciId() {
        return tedarikciId;
    }

    public void setTedarikciId(int tedarikciId) {
        this.tedarikciId = tedarikciId;
    }
}
