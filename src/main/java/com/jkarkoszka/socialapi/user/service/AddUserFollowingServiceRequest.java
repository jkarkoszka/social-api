package com.jkarkoszka.socialapi.user.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
public class AddUserFollowingServiceRequest {

    private final String userId;

}
