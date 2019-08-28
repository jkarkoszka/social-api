package com.jkarkoszka.socialapi.post;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostCreatorTest {

    PostCreator postCreator = new PostCreator();

    @Test
    public void shouldCreatePost() {
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var message = "message123";
        var addPostRequest = new AddPostRequest(message);
        var expectedPost = Post.builder()
                .userId(currentUserId)
                .message(message)
                .build();

        //when
        var post = postCreator.create(currentUserId, addPostRequest);

        //then
        assertThat(post).isEqualToIgnoringGivenFields(expectedPost, "id", "createdDate");
        assertThat(post.getCreatedDate()).isNotNull();
        assertThat(post.getUserId()).isNotNull();
    }
}