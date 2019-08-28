package com.jkarkoszka.socialapi.helper;

import com.jkarkoszka.socialapi.user.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TestUserCreator {

    public User create(ObjectId id) {
        return User.builder().id(id).followingUsers(Set.of()).build();
    }

    public User create(ObjectId id, Set<ObjectId> followingUsers) {
        return User.builder().id(id).followingUsers(followingUsers).build();
    }
}
