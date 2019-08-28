package com.jkarkoszka.socialapi.post;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PostCreator {

    public Post create(ObjectId currentUserId, AddPostRequest addPostRequest) {
        return Post.builder()
                .id(new ObjectId())
                .message(addPostRequest.getMessage())
                .userId(currentUserId)
                .createdDate(new Date())
                .build();
    }
}
