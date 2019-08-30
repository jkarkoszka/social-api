package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.Post;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
class PostCreator {

    Post create(ObjectId currentUserId, AddPostServiceRequest addPostServiceRequest) {
        return Post.builder()
                .id(new ObjectId())
                .message(addPostServiceRequest.getMessage())
                .userId(currentUserId)
                .createdDate(new Date())
                .build();
    }
}
