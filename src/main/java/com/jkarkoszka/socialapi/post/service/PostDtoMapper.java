package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.Post;
import org.springframework.stereotype.Component;

@Component
class PostDtoMapper {

    PostDto map(Post post) {
        return PostDto.builder()
                .id(post.getId().toHexString())
                .message(post.getMessage())
                .userId(post.getUserId().toHexString())
                .createdDate(post.getCreatedDate())
                .build();
    }
}
