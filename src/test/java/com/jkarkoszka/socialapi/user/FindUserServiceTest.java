package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    FindUserService findUserService;

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
