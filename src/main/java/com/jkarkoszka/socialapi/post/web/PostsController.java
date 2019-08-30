package com.jkarkoszka.socialapi.post.web;

import com.jkarkoszka.socialapi.post.service.AddPostService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
class PostsController {

    private final AddPostServiceRequestMapper addPostServiceRequestMapper;
    private final AddPostService addPostService;
    private final AddPostRestResponseMapper addPostRestResponseMapper;

    @PostMapping
    @ApiImplicitParam(name = "X-Current-User-Id", value = "Current User ID", required = true, paramType = "header",
            dataTypeClass = String.class, example = "507f1f77bcf86cd799439011")
    @ResponseStatus(HttpStatus.CREATED)
    AddPostRestResponse addPost(@RequestHeader("X-Current-User-Id") ObjectId currentUserId,
                                @RequestBody @Valid AddPostRestRequest addPostRestRequest) {
        var addPostServiceRequest = addPostServiceRequestMapper.map(addPostRestRequest);
        var postDto = addPostService.addPost(currentUserId, addPostServiceRequest);
        return addPostRestResponseMapper.map(postDto);
    }
}
