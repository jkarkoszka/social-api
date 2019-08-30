package com.jkarkoszka.socialapi.user.web;

import com.jkarkoszka.socialapi.user.service.UserDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AddUserFollowingRestResponseMapperTest {

    AddUserFollowingRestResponseMapper addUserFollowingRestResponseMapper = new AddUserFollowingRestResponseMapper();

    @Test
    public void shouldMapToAddUserFollowingResponse() {
        //given
        var userId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserIdAsHexString = "507f1f77bcf86cd799439012";
        var secondFollowingUserIdAsHexString = "507f1f77bcf86cd799439013";
        var firstFollowingUserId = new ObjectId(firstFollowingUserIdAsHexString);
        var secondFollowingUserId = new ObjectId(secondFollowingUserIdAsHexString);
        var userDto = UserDto.builder().id(userId).followingUsers(Set.of(firstFollowingUserId, secondFollowingUserId)).build();
        var expectedAddUserFollowingResponse = new AddUserFollowingRestResponse(Set.of(firstFollowingUserIdAsHexString,
                secondFollowingUserIdAsHexString));

        //when
        var addUserFollowingResponse = addUserFollowingRestResponseMapper.map(userDto);

        //then
        assertThat(addUserFollowingResponse).isEqualToComparingFieldByField(expectedAddUserFollowingResponse);
    }
}
