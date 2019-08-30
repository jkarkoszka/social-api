package com.jkarkoszka.socialapi.user.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
class AddUserFollowingRestRequest {

    private final String userId;

}
