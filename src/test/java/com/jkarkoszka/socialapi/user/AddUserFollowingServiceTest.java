package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddUserFollowingServiceTest {

    @Mock
    FindUserService findUserService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserCreator userCreator;
    @Mock
    AddUserFollowingResponseMapper addUserFollowingResponseMapper;
    @InjectMocks
    AddUserFollowingService addUserFollowingService;

    @Test
    public void shouldAddUserFollowingService() {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439012");
        var userToFollowIdAsHexString = "507f1f77bcf86cd799439013";
        var userToFollowId = new ObjectId(userToFollowIdAsHexString);
        var addUserFollowingRequest = new AddUserFollowingRequest(userToFollowIdAsHexString);
        var currentUser = User.builder().id(currentUserId).build();
        var userToFollow = User.builder().id(userToFollowId).build();
        var followingUsers = Set.of(userToFollowId);
        var followingUsersAsStrings = Set.of(userToFollowIdAsHexString);
        var currentUserToSave = User.builder().id(currentUserId).followingUsers(followingUsers).build();
        var savedUser = User.builder().id(currentUserId).followingUsers(followingUsers).build();
        var expectedAddUserFollowingResponse = new AddUserFollowingResponse(followingUsersAsStrings);
        when(findUserService.findUser(currentUserId)).thenReturn(currentUser);
        when(findUserService.findUser(userToFollowId)).thenReturn(userToFollow);
        when(userCreator.createWithNewFollowingUser(currentUser, userToFollow)).thenReturn(currentUserToSave);
        when(userRepository.save(currentUserToSave)).thenReturn(savedUser);
        when(addUserFollowingResponseMapper.map(savedUser)).thenReturn(expectedAddUserFollowingResponse);

        //when
        var addUserFollowingResponse = addUserFollowingService.addUserFollowing(currentUserId, addUserFollowingRequest);

        //then
        assertThat(addUserFollowingResponse).isEqualToComparingFieldByField(expectedAddUserFollowingResponse);
    }
}