package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.Post;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class PostDtoMapperTest {

    PostDtoMapper postDtoMapper = new PostDtoMapper();

    @Test
    public void shouldMapToPostDto() {
        //given
        var postIdAsHexString = "507f1f77bcf86cd799439011";
        var userIdAsHexString = "507f1f77bcf86cd799439012";
        var postId = new ObjectId(postIdAsHexString);
        var userId = new ObjectId(userIdAsHexString);
        var message = "message123";
        var createdDate = new Date(156698112015L);
        var post = Post.builder()
                .id(postId)
                .message(message)
                .createdDate(createdDate)
                .userId(userId)
                .build();
        var expectedPostDto = PostDto.builder()
                .id(postIdAsHexString)
                .message(message)
                .createdDate(createdDate)
                .userId(userIdAsHexString)
                .build();
        //when
        var postDto = postDtoMapper.map(post);

        //then
        assertThat(postDto).isEqualToComparingFieldByField(expectedPostDto);
    }
}
