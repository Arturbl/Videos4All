package com.Videos4All.Videos4All.controller;

import com.Videos4All.Videos4All.model.POJO.VideoResponse;
import com.Videos4All.Videos4All.model.Video;
import com.Videos4All.Videos4All.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping(value = "/video/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            videoService.upload(file);
            return ResponseEntity.ok(file);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<String>("something went wrong", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/video/findAll")
    public List<VideoResponse> getVideos() {
        return videoService.getVideos()
                .stream()
                .map(this::mapToFileResponse)
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

    private VideoResponse mapToFileResponse(Video video) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(video.getId())
                .toUriString();
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(video.getId());
        videoResponse.setName(video.getName());
        videoResponse.setContentType(video.getContentType());
        videoResponse.setSize(video.getSize());
        videoResponse.setUrl(downloadURL);
        return videoResponse;
    }

}
