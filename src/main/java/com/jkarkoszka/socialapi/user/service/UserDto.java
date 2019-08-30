package com.jkarkoszka.socialapi.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Set;

@AllArgsConstructor
@Getter
@Builder
public class UserDto {

    private final ObjectId id;
    private final Set<ObjectId> followingUsers;
}
