package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddUserFollowingResponseMapper {

    public AddUserFollowingResponse map(User user) {
        var followingUsers = user.getFollowingUsers().stream()
                .map(ObjectId::toHexString)
                .collect(Collectors.toSet());
        return new AddUserFollowingResponse(followingUsers);
    }
}
