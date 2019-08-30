package com.jkarkoszka.socialapi.helper;

import com.jkarkoszka.socialapi.post.repository.Post;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestPostCreator {

    public Post create(String message, ObjectId userId, Date date) {
        return Post.builder()
                .id(new ObjectId())
                .message(message)
                .userId(userId)
                .createdDate(date)
                .build();
    }
}
