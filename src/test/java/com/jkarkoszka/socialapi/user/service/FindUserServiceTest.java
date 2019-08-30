package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.User;
import com.jkarkoszka.socialapi.user.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserDtoMapper userDtoMapper;
    @InjectMocks
    FindUserService findUserService;

    @Test
    public void shouldReturnUser() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        User user = User.builder().id(userId).build();
        UserDto expectedUserDto = UserDto.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userDtoMapper.map(user)).thenReturn(expectedUserDto);

        //when
        var userDto = findUserService.findUser(userId);

        //then
        assertThat(userDto).isEqualToComparingFieldByField(expectedUserDto);
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExistOnFindFollowingUsersPosts() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when//then
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            findUserService.findUser(userId);
        });
    }
}
