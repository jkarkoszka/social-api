package com.jkarkoszka.socialapi.post.web;

import com.jkarkoszka.socialapi.post.service.PostDto;
import org.springframework.stereotype.Component;

@Component
class AddPostRestResponseMapper {

    AddPostRestResponse map(PostDto postDto) {
        return new AddPostRestResponse(postDto.getId());
    }
}
