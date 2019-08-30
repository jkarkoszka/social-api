package com.jkarkoszka.socialapi.user.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
class AddUserFollowingRestResponse {

    private final Set<String> followingUsers;
}
