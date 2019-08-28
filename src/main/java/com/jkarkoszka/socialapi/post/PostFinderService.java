package com.jkarkoszka.socialapi.post;

import com.jkarkoszka.socialapi.user.FindUserService;
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
    private final PostRestMapper postRestMapper;

    public Page<PostRest> findUsersPosts(ObjectId userId, Pageable pageable) {
        findUserService.findUser(userId);
        return postRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable)
                .map(postRestMapper::map);
    }

    public Page<PostRest> findFollowingUsersPosts(ObjectId userId, Pageable pageable) {
        var user = findUserService.findUser(userId);
        return postRepository.findByUserIdInOrderByCreatedDateDesc(user.getFollowingUsers(), pageable)
                .map(postRestMapper::map);
    }
}
