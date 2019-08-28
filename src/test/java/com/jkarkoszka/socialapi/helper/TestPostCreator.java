package com.jkarkoszka.socialapi.helper;

import com.jkarkoszka.socialapi.post.Post;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestPostCreator {

    public Post create(String message, ObjectId userId) {
        return Post.builder()
                .id(new ObjectId())
                .message(message)
                .userId(userId)
                .createdDate(new Date())
                .build();
    }
}
