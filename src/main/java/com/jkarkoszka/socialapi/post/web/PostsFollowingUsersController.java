package com.jkarkoszka.socialapi.post.web;

import com.jkarkoszka.socialapi.post.service.PostFinderService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/followingUsers")
@AllArgsConstructor
class PostsFollowingUsersController {

    private final PostFinderService postFinderService;
    private final PostRestMapper postRestMapper;

    @GetMapping
    @ApiImplicitParam(name = "X-Current-User-Id", value = "Current User ID", required = true, paramType = "header",
            dataTypeClass = String.class, example = "507f1f77bcf86cd799439011")
    Page<PostRest> getFollowingUsersPosts(@RequestHeader("X-Current-User-Id") ObjectId currentUserId,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return postFinderService.findFollowingUsersPosts(currentUserId, PageRequest.of(page, size))
                .map(postRestMapper::map);
    }
}
