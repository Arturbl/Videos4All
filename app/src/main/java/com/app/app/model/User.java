package com.app.app.model;

public class User {

    private final String accessToken;
    private final String username;


    public User(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }


    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
