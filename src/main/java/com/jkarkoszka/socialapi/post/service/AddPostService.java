package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.PostRepository;
import com.jkarkoszka.socialapi.user.service.CreateUserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddPostService {

    private final PostCreator postCreator;
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;
    private final CreateUserService createUserService;

    public PostDto addPost(ObjectId currentUserId, AddPostServiceRequest addPostServiceRequest) {
        createUserService.createIfNotExist(currentUserId);
        var post = postCreator.create(currentUserId, addPostServiceRequest);
        var savedPost = postRepository.save(post);
        return postDtoMapper.map(savedPost);
    }
}
