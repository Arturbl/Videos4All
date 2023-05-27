package com.Videos4All.Videos4All.model.pojo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthRequest {

    @NotNull @Length(min = 1, max = 100)
    private String username;

    @NotNull @Length(min = 1, max = 100)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
