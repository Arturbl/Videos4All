package com.Videos4All.Videos4All.repo;


import com.Videos4All.Videos4All.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepo extends JpaRepository<Video, String> {
    Optional<Video> findByName(String name);

}
