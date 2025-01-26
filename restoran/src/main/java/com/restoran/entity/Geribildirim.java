package com.restoran.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Geribildirim {
    private int id;
    private String yorum;

    @JsonProperty("user_id")
    private int userId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
