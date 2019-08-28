package com.jkarkoszka.socialapi.user;

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
    @InjectMocks
    CreateUserService createUserService;

    @Test
    public void shouldCreateNewUserIfNotExist() {
        //given
        ObjectId userId = new ObjectId("507f1f77bcf86cd799439011");
        User createdUser = User.builder().id(userId).build();
        User expectedUser = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userCreator.createEmpty(userId)).thenReturn(createdUser);
        when(userRepository.save(createdUser)).thenReturn(expectedUser);

        //when
        var user = createUserService.createIfNotExist(userId);

        //then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    public void shouldReturnUserIfExists() {
        //given
        ObjectId userId = new ObjectId("507f1f77bcf86cd799439011");
        User expectedUser = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        //when
        var user = createUserService.createIfNotExist(userId);

        //then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }
}
