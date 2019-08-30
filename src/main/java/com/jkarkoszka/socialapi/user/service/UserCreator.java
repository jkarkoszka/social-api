package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
class UserCreator {

    User createEmpty(ObjectId id) {
        return User.builder()
                .id(id)
                .followingUsers(Collections.emptySet())
                .build();
    }

    User createWithNewFollowingUser(UserDto userDto, UserDto userDtoToFollow) {
        var followingUsers = prepareFollowingUsers(userDto, userDtoToFollow);
        return User.builder()
                .id(userDto.getId())
                .followingUsers(followingUsers)
                .build();
    }

    private Set<ObjectId> prepareFollowingUsers(UserDto userDto, UserDto userDtoToFollow) {
        var newFollowingUser = new HashSet<>(userDto.getFollowingUsers());
        newFollowingUser.add(userDtoToFollow.getId());
        return newFollowingUser;
    }
}
