package com.jkarkoszka.socialapi.post;

import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

    private final AddPostService addPostService;

    @PostMapping
    @ApiImplicitParam(name = "X-Current-User-Id", value = "Current User ID", required = true, paramType = "header",
            dataTypeClass = String.class, example = "507f1f77bcf86cd799439011")
    @ResponseStatus(HttpStatus.CREATED)
    public AddPostResponse addPost(@RequestHeader("X-Current-User-Id") ObjectId currentUserId,
                                   @RequestBody @Valid AddPostRequest addPostRequest) {
        return addPostService.addPost(currentUserId, addPostRequest);
    }
}
