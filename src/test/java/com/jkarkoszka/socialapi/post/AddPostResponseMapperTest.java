package com.jkarkoszka.socialapi.post;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class AddPostResponseMapperTest {

    private final AddPostResponseMapper addPostResponseMapper = new AddPostResponseMapper();

    @Test
    public void shouldMapToAddPostResponse() {
        //given
        var idAsHexString = "507f1f77bcf86cd799439011";
        var post = Post.builder()
                .id(new ObjectId(idAsHexString))
                .message("message123")
                .userId(new ObjectId("507f1f77bcf86cd799439012"))
                .createdDate(new Date(156698112013L))
                .build();
        var expectedAddPostResponse = new AddPostResponse(idAsHexString);

        //when
        var addPostResponse = addPostResponseMapper.map(post);

        //then
        assertThat(addPostResponse).isEqualToComparingFieldByField(expectedAddPostResponse);
    }
}
