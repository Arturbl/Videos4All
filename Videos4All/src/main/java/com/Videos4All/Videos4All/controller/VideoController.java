package com.Videos4All.Videos4All.controller;

import com.Videos4All.Videos4All.model.Video;
import com.Videos4All.Videos4All.model.VideoMapper;
import com.Videos4All.Videos4All.model.pojo.VideoResponse;
import com.Videos4All.Videos4All.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = {"application/json"})
public class VideoController {

    private final VideoService videoService;
    private final VideoMapper videoMapper;

    @Autowired
    public VideoController(VideoService videoService, VideoMapper videoMapper) {
        this.videoService = videoService;
        this.videoMapper = videoMapper;
    }

    @PostMapping(value = "/video/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) throws IOException {
        if(Objects.equals(username, "admin")) {
            Video upload = videoService.upload(file);
            return ResponseEntity.ok(videoMapper.parse(upload));
        }
        return new ResponseEntity<String>("You dont have enough permissions.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/video/findAll")
    public List<VideoResponse> getVideos() {
        return videoService.getVideos()
                .stream()
                .map(this.videoMapper::parse)
                .collect(Collectors.toList());
    }



    @GetMapping(value = "/video/find/{name}")
    public ResponseEntity<?> getVideo(@PathVariable String name) {
        Optional<Video> videoOpt = videoService.getVideo(name);
        if (!videoOpt.isPresent()) {
            return new ResponseEntity<String>("Video " + name + " not found", HttpStatus.NOT_FOUND);
        }
        Video video = videoOpt.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getName() + "\"")
                .contentType(MediaType.valueOf(video.getContentType()))
                .body(video.getData());
    }

}
