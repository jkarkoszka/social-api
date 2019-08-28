package com.jkarkoszka.socialapi.post;

import com.jkarkoszka.socialapi.user.CreateUserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddPostServiceTest {

    @Mock
    PostCreator postCreator;
    @Mock
    PostRepository postRepository;
    @Mock
    AddPostResponseMapper addPostResponseMapper;
    @Mock
    CreateUserService createUserService;
    @InjectMocks
    AddPostService addPostService;

    @Test
    public void shouldAddPost() {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var addPostRequest = new AddPostRequest("message123");
        var postId = new ObjectId("507f1f77bcf86cd799439012");
        var post = Post.builder().id(postId).build();
        var savedPost = Post.builder().id(postId).build();
        var expectedAddPostResponse = new AddPostResponse(postId.toHexString());
        when(postCreator.create(currentUserId, addPostRequest)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(addPostResponseMapper.map(savedPost)).thenReturn(expectedAddPostResponse);

        //when
        var addPostResponse = addPostService.addPost(currentUserId, addPostRequest);

        //then
        assertThat(addPostResponse).isEqualToComparingFieldByField(expectedAddPostResponse);
        verify(createUserService).createIfNotExist(currentUserId);
        verifyNoMoreInteractions(postCreator, postRepository, addPostResponseMapper, createUserService);
    }
}
