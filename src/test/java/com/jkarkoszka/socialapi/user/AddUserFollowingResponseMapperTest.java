package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AddUserFollowingResponseMapperTest {

    AddUserFollowingResponseMapper addUserFollowingResponseMapper = new AddUserFollowingResponseMapper();

    @Test
    public void shouldMapToAddUserFollowingResponse() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserId = "507f1f77bcf86cd799439012";
        var secondFollowingUserId = "507f1f77bcf86cd799439013";
        var user = User.builder().id(userId).followingUsers(Set.of(new ObjectId(firstFollowingUserId),
                new ObjectId(secondFollowingUserId))).build();
        var expectedAddUserFollowingResponse = new AddUserFollowingResponse(Set.of(firstFollowingUserId,
                secondFollowingUserId));

        //when
        var addUserFollowingResponse = addUserFollowingResponseMapper.map(user);

        //then
        assertThat(addUserFollowingResponse).isEqualToComparingFieldByField(expectedAddUserFollowingResponse);
    }
}
