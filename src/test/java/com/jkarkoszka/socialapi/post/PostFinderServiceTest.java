package com.jkarkoszka.socialapi.post;

import com.jkarkoszka.socialapi.user.User;
import com.jkarkoszka.socialapi.user.UserNotFoundException;
import com.jkarkoszka.socialapi.user.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
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
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PostFinderServiceTest {

    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    PostRestMapper postRestMapper;
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
        var firstPostRest = PostRest.builder().id(firstPostId.toHexString()).build();
        var secondPostRest = PostRest.builder().id(secondPostId.toHexString()).build();
        var pageOfPosts = new PageImpl<>(List.of(firstPost, secondPost));
        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().build()));
        when(postRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable)).thenReturn(pageOfPosts);
        when(postRestMapper.map(firstPost)).thenReturn(firstPostRest);
        when(postRestMapper.map(secondPost)).thenReturn(secondPostRest);

        //when
        var usersPosts = postFinderService.findUsersPosts(userId, pageable);

        //then
        assertThat(usersPosts).containsExactly(firstPostRest, secondPostRest);
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExistOnFindUserPosts() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var pageable = PageRequest.of(0, 10);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when//then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            postFinderService.findUsersPosts(userId, pageable);
        });
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
        var firstPostRest = PostRest.builder().id(firstPostId.toHexString()).build();
        var secondPostRest = PostRest.builder().id(secondPostId.toHexString()).build();
        var pageOfPosts = new PageImpl<>(List.of(firstPost, secondPost));
        var followingUsers = Set.of(followingUserId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().followingUsers(followingUsers).build()));
        when(postRepository.findByUserIdInOrderByCreatedDateDesc(followingUsers, pageable)).thenReturn(pageOfPosts);
        when(postRestMapper.map(firstPost)).thenReturn(firstPostRest);
        when(postRestMapper.map(secondPost)).thenReturn(secondPostRest);

        //when
        var usersPosts = postFinderService.findFollowingUsersPosts(userId, pageable);

        //then
        assertThat(usersPosts).containsExactly(firstPostRest, secondPostRest);
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExistOnFindFollowingUsersPosts() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var pageable = PageRequest.of(0, 10);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when//then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            postFinderService.findFollowingUsersPosts(userId, pageable);
        });
    }
}