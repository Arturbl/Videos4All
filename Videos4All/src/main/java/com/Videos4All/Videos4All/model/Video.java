package com.Videos4All.Videos4All.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @Column
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(name = "data", columnDefinition = "bytea")
    private byte[] data;

    public Video() {}

    public String getUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(getId())
                .toUriString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setName(String video_file) {
        this.name = video_file;
    }
}
