package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreatorTest {

    UserCreator userCreator = new UserCreator();

    @Test
    public void shouldCreateEmptyUser() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var expectedUser = User.builder()
                .id(userId)
                .followingUsers(Collections.emptySet())
                .build();

        //when
        var user = userCreator.createEmpty(userId);

        //then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    public void shouldCreateUserWithNewFollowingUser() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserId = new ObjectId("507f1f77bcf86cd799439012");
        var user = User.builder()
                .id(userId)
                .followingUsers(Set.of(firstFollowingUserId))
                .build();
        var secondFollowingUserId = new ObjectId("507f1f77bcf86cd799439013");
        var userToFollow = User.builder()
                .id(secondFollowingUserId)
                .followingUsers(Set.of())
                .build();
        var expectedUpdatedUser = User.builder()
                .id(userId)
                .followingUsers(Set.of(firstFollowingUserId, secondFollowingUserId))
                .build();

        //when
        var updatedUser = userCreator.createWithNewFollowingUser(user, userToFollow);

        //then
        assertThat(updatedUser).isEqualToComparingFieldByField(expectedUpdatedUser);
    }
}
