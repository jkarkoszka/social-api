package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.User;
import com.jkarkoszka.socialapi.user.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserCreator userCreator;
    @Mock
    UserDtoMapper userDtoMapper;
    @InjectMocks
    CreateUserService createUserService;

    @Test
    public void shouldCreateNewUserIfNotExist() {
        //given
        ObjectId userId = new ObjectId("507f1f77bcf86cd799439011");
        User createdUser = User.builder().id(userId).build();
        User savedUser = User.builder().id(userId).build();
        UserDto expectedUserDto = UserDto.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userCreator.createEmpty(userId)).thenReturn(createdUser);
        when(userRepository.save(createdUser)).thenReturn(savedUser);
        when(userDtoMapper.map(savedUser)).thenReturn(expectedUserDto);

        //when
        var userDto = createUserService.createIfNotExist(userId);

        //then
        assertThat(userDto).isEqualToComparingFieldByField(expectedUserDto);
    }

    @Test
    public void shouldReturnUserIfExists() {
        //given
        ObjectId userId = new ObjectId("507f1f77bcf86cd799439011");
        User user = User.builder().id(userId).build();
        UserDto expectedUserDto = UserDto.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userDtoMapper.map(user)).thenReturn(expectedUserDto);

        //when
        var userDto = createUserService.createIfNotExist(userId);

        //then
        assertThat(userDto).isEqualToComparingFieldByField(expectedUserDto);
    }
}
