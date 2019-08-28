package com.jkarkoszka.socialapi.post;

import org.springframework.stereotype.Component;

@Component
public class PostRestMapper {

    public PostRest map(Post post) {
        return PostRest.builder()
                .id(post.getId().toHexString())
                .message(post.getMessage())
                .userId(post.getUserId().toHexString())
                .createdDate(post.getCreatedDate())
                .build();
    }
}
