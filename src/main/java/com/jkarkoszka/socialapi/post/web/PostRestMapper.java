package com.jkarkoszka.socialapi.post.web;

import com.jkarkoszka.socialapi.post.service.PostDto;
import org.springframework.stereotype.Component;

@Component
class PostRestMapper {

    PostRest map(PostDto postDto) {
        return PostRest.builder()
                .id(postDto.getId())
                .message(postDto.getMessage())
                .userId(postDto.getUserId())
                .createdDate(postDto.getCreatedDate())
                .build();
    }
}
