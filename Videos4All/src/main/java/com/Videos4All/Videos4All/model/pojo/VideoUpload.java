package com.Videos4All.Videos4All.model.pojo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class VideoUpload {

    @NotNull
    @Length(min = 1, max = 100)
    private String username;

    @NotNull @Length(min = 1, max = 5000)
    private String video_file;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVideo_file() {
        return video_file;
    }

    public void setVideo_file(String video_file) {
        this.video_file = video_file;
    }
}
