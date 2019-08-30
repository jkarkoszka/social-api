package com.jkarkoszka.socialapi.user.web;

import com.jkarkoszka.socialapi.user.service.AddUserFollowingService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/followingUsers")
@AllArgsConstructor
class UsersMeFollowingController {

    private final AddUserFollowingServiceRequestMapper addUserFollowingServiceRequestMapper;
    private final AddUserFollowingService addUserFollowingService;
    private final AddUserFollowingRestResponseMapper addUserFollowingRestResponseMapper;

    @PostMapping
    @ApiImplicitParam(name = "X-Current-User-Id", value = "Current User ID", required = true, paramType = "header",
            dataTypeClass = String.class, example = "507f1f77bcf86cd799439011")
    AddUserFollowingRestResponse addFollowing(@RequestHeader("X-Current-User-Id") ObjectId currentUserId,
                                                     @RequestBody AddUserFollowingRestRequest addUserFollowingRestRequest) {
        var addUserFollowingServiceRequest = addUserFollowingServiceRequestMapper.map(addUserFollowingRestRequest);
        var userDto = addUserFollowingService.addUserFollowing(currentUserId, addUserFollowingServiceRequest);
        return addUserFollowingRestResponseMapper.map(userDto);
    }
}
