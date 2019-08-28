package com.jkarkoszka.socialapi.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class AddUserFollowingRequest {

    private final String userId;

    public String getUserId() {
        return userId;
    }
}
