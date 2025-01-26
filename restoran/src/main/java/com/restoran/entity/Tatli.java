package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tatli {
    private int id;
    private String name;

    @JsonProperty("menu_id")
    private int menuId;

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

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
