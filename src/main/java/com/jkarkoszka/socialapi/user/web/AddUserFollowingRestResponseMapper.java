package com.jkarkoszka.socialapi.user.web;

import com.jkarkoszka.socialapi.user.service.UserDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class AddUserFollowingRestResponseMapper {

    public AddUserFollowingRestResponse map(UserDto userDto) {
        var followingUsers = userDto.getFollowingUsers().stream()
                .map(ObjectId::toHexString)
                .collect(Collectors.toSet());
        return new AddUserFollowingRestResponse(followingUsers);
    }
}
