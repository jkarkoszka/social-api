package com.jkarkoszka.socialapi.user.web;

import com.jkarkoszka.socialapi.user.service.AddUserFollowingServiceRequest;
import org.springframework.stereotype.Component;

@Component
class AddUserFollowingServiceRequestMapper {

    AddUserFollowingServiceRequest map(AddUserFollowingRestRequest addUserFollowingRestRequest) {
        return new AddUserFollowingServiceRequest(addUserFollowingRestRequest.getUserId());
    }
}
