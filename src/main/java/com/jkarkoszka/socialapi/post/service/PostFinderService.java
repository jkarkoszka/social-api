package com.jkarkoszka.socialapi.post.service;

import com.jkarkoszka.socialapi.post.repository.PostRepository;
import com.jkarkoszka.socialapi.user.service.FindUserService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostFinderService {

    private final PostRepository postRepository;
    private final FindUserService findUserService;
    private final PostDtoMapper postDtoMapper;

    public Page<PostDto> findUsersPosts(ObjectId userId, Pageable pageable) {
        findUserService.findUser(userId);
        return postRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable)
                .map(postDtoMapper::map);
    }

    public Page<PostDto> findFollowingUsersPosts(ObjectId userId, Pageable pageable) {
        var userDto = findUserService.findUser(userId);
        return postRepository.findByUserIdInOrderByCreatedDateDesc(userDto.getFollowingUsers(), pageable)
                .map(postDtoMapper::map);
    }
}
