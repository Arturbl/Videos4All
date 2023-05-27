package com.Videos4All.Videos4All.model;

import com.Videos4All.Videos4All.model.pojo.VideoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoResponse parse(Video video);

}
