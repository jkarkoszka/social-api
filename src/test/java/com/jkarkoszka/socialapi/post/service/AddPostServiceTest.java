package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.Post;
import com.jkarkoszka.socialapi.post.repository.PostRepository;
import com.jkarkoszka.socialapi.user.service.CreateUserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddPostServiceTest {

    @Mock
    PostCreator postCreator;
    @Mock
    PostRepository postRepository;
    @Mock
    PostDtoMapper postDtoMapper;
    @Mock
    CreateUserService createUserService;
    @InjectMocks
    AddPostService addPostService;

    @Test
    public void shouldAddPost() {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var addPostServiceRequest = new AddPostServiceRequest("message123");
        var postId = new ObjectId("507f1f77bcf86cd799439012");
        var post = Post.builder().id(postId).build();
        var savedPost = Post.builder().id(postId).build();
        var expectedPostDto = PostDto.builder().id(postId.toHexString()).build();
        when(postCreator.create(currentUserId, addPostServiceRequest)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(postDtoMapper.map(savedPost)).thenReturn(expectedPostDto);

        //when
        var postDto = addPostService.addPost(currentUserId, addPostServiceRequest);

        //then
        assertThat(postDto).isEqualToComparingFieldByField(expectedPostDto);
        verify(createUserService).createIfNotExist(currentUserId);
    }
}
