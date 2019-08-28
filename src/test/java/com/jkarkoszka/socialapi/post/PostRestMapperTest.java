package com.jkarkoszka.socialapi.post;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class PostRestMapperTest {

    PostRestMapper postRestMapper = new PostRestMapper();

    @Test
    public void shouldMapToPostRest() {
        //given
        var postIdAsHexString = "507f1f77bcf86cd799439011";
        var userIdAsHexString = "507f1f77bcf86cd799439012";
        var message = "message1";
        var createdDate = new Date(156698112013L);
        var post = Post.builder()
                .id(new ObjectId(postIdAsHexString))
                .message(message)
                .userId(new ObjectId(userIdAsHexString))
                .createdDate(createdDate)
                .build();
        var expectedPostRest = PostRest.builder()
                .id(postIdAsHexString)
                .message(message)
                .userId(userIdAsHexString)
                .createdDate(createdDate)
                .build();

        //when
        var postRest = postRestMapper.map(post);

        //then
        assertThat(postRest).isEqualToIgnoringGivenFields(expectedPostRest);
    }
}
