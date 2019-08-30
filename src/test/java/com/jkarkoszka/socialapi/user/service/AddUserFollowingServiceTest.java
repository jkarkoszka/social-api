package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.User;
import com.jkarkoszka.socialapi.user.repository.UserRepository;
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
    UserDtoMapper userDtoMapper;
    @InjectMocks
    AddUserFollowingService addUserFollowingService;

    @Test
    public void shouldAddUserFollowingService() {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439012");
        var userToFollowIdAsHexString = "507f1f77bcf86cd799439013";
        var userToFollowId = new ObjectId(userToFollowIdAsHexString);
        var addUserFollowingServiceRequest = new AddUserFollowingServiceRequest(userToFollowIdAsHexString);
        var currentUserDto = UserDto.builder().id(currentUserId).build();
        var userDtoToFollow = UserDto.builder().id(userToFollowId).build();
        var followingUsers = Set.of(userToFollowId);
        var currentUserToSave = User.builder().id(currentUserId).followingUsers(followingUsers).build();
        var savedUpdatedCurrentUser = User.builder().id(currentUserId).followingUsers(followingUsers).build();
        var expectedUserDto = UserDto.builder().id(currentUserId).followingUsers(followingUsers).build();
        when(findUserService.findUser(currentUserId)).thenReturn(currentUserDto);
        when(findUserService.findUser(userToFollowId)).thenReturn(userDtoToFollow);
        when(userCreator.createWithNewFollowingUser(currentUserDto, userDtoToFollow)).thenReturn(currentUserToSave);
        when(userRepository.save(currentUserToSave)).thenReturn(savedUpdatedCurrentUser);
        when(userDtoMapper.map(savedUpdatedCurrentUser)).thenReturn(expectedUserDto);

        //when
        var userDto = addUserFollowingService.addUserFollowing(currentUserId, addUserFollowingServiceRequest);

        //then
        assertThat(userDto).isEqualToComparingFieldByField(expectedUserDto);
    }
}
