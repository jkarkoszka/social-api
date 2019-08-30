package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.Post;
import com.jkarkoszka.socialapi.post.repository.PostRepository;
import com.jkarkoszka.socialapi.user.service.FindUserService;
import com.jkarkoszka.socialapi.user.service.UserDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PostFinderServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    FindUserService findUserService;
    @Mock
    PostDtoMapper postDtoMapper;
    @InjectMocks
    PostFinderService postFinderService;

    @Test
    public void shouldFindUsersPosts() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var pageable = PageRequest.of(0, 10);
        var firstPostId = new ObjectId("507f1f77bcf86cd799439012");
        var secondPostId = new ObjectId("507f1f77bcf86cd799439013");
        var firstPost = Post.builder().id(firstPostId).build();
        var secondPost = Post.builder().id(secondPostId).build();
        var firstPostDto = PostDto.builder().id(firstPostId.toHexString()).build();
        var secondPostDto = PostDto.builder().id(secondPostId.toHexString()).build();
        var pageOfPosts = new PageImpl<>(List.of(firstPost, secondPost));
        when(findUserService.findUser(userId)).thenReturn(UserDto.builder().build());
        when(postRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable)).thenReturn(pageOfPosts);
        when(postDtoMapper.map(firstPost)).thenReturn(firstPostDto);
        when(postDtoMapper.map(secondPost)).thenReturn(secondPostDto);

        //when
        var usersPosts = postFinderService.findUsersPosts(userId, pageable);

        //then
        assertThat(usersPosts).containsExactly(firstPostDto, secondPostDto);
    }

    @Test
    public void shouldFindFollowingUsersPosts() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var followingUserId = new ObjectId("507f1f77bcf86cd799439014");
        var pageable = PageRequest.of(0, 10);
        var firstPostId = new ObjectId("507f1f77bcf86cd799439012");
        var secondPostId = new ObjectId("507f1f77bcf86cd799439013");
        var firstPost = Post.builder().id(firstPostId).build();
        var secondPost = Post.builder().id(secondPostId).build();
        var firstPostDto = PostDto.builder().id(firstPostId.toHexString()).build();
        var secondPostDto = PostDto.builder().id(secondPostId.toHexString()).build();
        var pageOfPosts = new PageImpl<>(List.of(firstPost, secondPost));
        var followingUsers = Set.of(followingUserId);
        when(findUserService.findUser(userId)).thenReturn(UserDto.builder().followingUsers(followingUsers).build());
        when(postRepository.findByUserIdInOrderByCreatedDateDesc(followingUsers, pageable)).thenReturn(pageOfPosts);
        when(postDtoMapper.map(firstPost)).thenReturn(firstPostDto);
        when(postDtoMapper.map(secondPost)).thenReturn(secondPostDto);

        //when
        var usersPosts = postFinderService.findFollowingUsersPosts(userId, pageable);

        //then
        assertThat(usersPosts).containsExactly(firstPostDto, secondPostDto);
    }
}
