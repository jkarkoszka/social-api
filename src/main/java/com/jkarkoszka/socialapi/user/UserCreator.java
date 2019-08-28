package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserCreator {

    public User createEmpty(ObjectId id) {
        return User.builder()
                .id(id)
                .followingUsers(Collections.emptySet())
                .build();
    }

    public User createWithNewFollowingUser(User user, User userToFollow) {
        var followingUsers = prepareFollowingUsers(user, userToFollow);
        return User.builder()
                .id(user.getId())
                .followingUsers(followingUsers)
                .build();
    }

    private Set<ObjectId> prepareFollowingUsers(User user, User userToFollow) {
        var newFollowingUser = new HashSet<>(user.getFollowingUsers());
        newFollowingUser.add(userToFollow.getId());
        return newFollowingUser;
    }
}
