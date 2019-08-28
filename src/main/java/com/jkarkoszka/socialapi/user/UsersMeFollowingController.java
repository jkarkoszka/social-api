package com.jkarkoszka.socialapi.user;

import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/me/followingUsers")
@AllArgsConstructor
public class UsersMeFollowingController {

    private final AddUserFollowingService addUserFollowingService;

    @PostMapping
    @ApiImplicitParam(name = "X-Current-User-Id", value = "Current User ID", required = true, paramType = "header",
            dataTypeClass = String.class, example = "507f1f77bcf86cd799439011")
    public AddUserFollowingResponse addFollowing(@RequestHeader("X-Current-User-Id") ObjectId currentUserId,
                                                 @RequestBody AddUserFollowingRequest addUserFollowingRequest) {
        return addUserFollowingService.addUserFollowing(currentUserId, addUserFollowingRequest);
    }
}
