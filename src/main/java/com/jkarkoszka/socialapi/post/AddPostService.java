package com.jkarkoszka.socialapi.post;

import com.jkarkoszka.socialapi.user.CreateUserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddPostService {

    private final PostCreator postCreator;
    private final PostRepository postRepository;
    private final AddPostResponseMapper addPostResponseMapper;
    private final CreateUserService createUserService;

    public AddPostResponse addPost(ObjectId currentUserId, AddPostRequest addPostRequest) {
        createUserService.createIfNotExist(currentUserId);
        var post = postCreator.create(currentUserId, addPostRequest);
        var savedPost = postRepository.save(post);
        return addPostResponseMapper.map(savedPost);
    }
}
