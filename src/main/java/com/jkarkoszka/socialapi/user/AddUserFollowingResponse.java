package com.jkarkoszka.socialapi.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class AddUserFollowingResponse {

    private final Set<String> followingUsers;
}
