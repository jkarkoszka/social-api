package com.jkarkoszka.socialapi.post;

import com.jkarkoszka.socialapi.user.UserNotFoundException;
import com.jkarkoszka.socialapi.user.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PostFinderService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostRestMapper postRestMapper;

    public Page<PostRest> findUsersPosts(ObjectId userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return postRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable)
                .map(postRestMapper::map);
    }

    public Page<PostRest> findFollowingUsersPosts(ObjectId userId, Pageable pageable) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return postRepository.findByUserIdInOrderByCreatedDateDesc(user.getFollowingUsers(), pageable)
                .map(postRestMapper::map);
    }
}
