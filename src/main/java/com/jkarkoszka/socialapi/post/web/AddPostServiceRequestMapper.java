package com.jkarkoszka.socialapi.post.web;

import com.jkarkoszka.socialapi.post.service.AddPostServiceRequest;
import org.springframework.stereotype.Component;

@Component
class AddPostServiceRequestMapper {

    AddPostServiceRequest map(AddPostRestRequest addPostRestRequest) {
        return new AddPostServiceRequest(addPostRestRequest.getMessage());
    }
}
