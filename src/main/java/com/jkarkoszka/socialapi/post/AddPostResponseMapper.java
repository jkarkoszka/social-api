package com.jkarkoszka.socialapi.post;

import org.springframework.stereotype.Component;

@Component
public class AddPostResponseMapper {

    public AddPostResponse map(Post post) {
        return new AddPostResponse(post.getId().toHexString());
    }
}
