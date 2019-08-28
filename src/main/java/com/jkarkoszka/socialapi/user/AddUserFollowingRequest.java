package com.jkarkoszka.socialapi.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
public class AddUserFollowingRequest {

    private final String userId;

}
