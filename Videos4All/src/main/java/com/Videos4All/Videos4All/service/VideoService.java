package com.Videos4All.Videos4All.service;

import com.Videos4All.Videos4All.model.Video;
import com.Videos4All.Videos4All.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepo videoRepo;

    public List<Video> getVideos() {
        return videoRepo.findAll();
    }

    public Optional<Video> getVideo(String name) {
        return videoRepo.findByName(name);
    }

    public Video upload(MultipartFile file) throws IOException {
        Video video = new Video();
        video.setName(
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()))
        );
        video.setContentType(file.getContentType());
        video.setSize(file.getSize());
        video.setData(file.getBytes());
        videoRepo.save(video);
        return video;
    }

}
