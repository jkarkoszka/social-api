package com.jkarkoszka.socialapi.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserFollowingService {

    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final AddUserFollowingResponseMapper addUserFollowingResponseMapper;

    public AddUserFollowingResponse addUserFollowing(ObjectId currentUserId, AddUserFollowingRequest addUserFollowingRequest) {
        var currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException(currentUserId));
        var userToFollow = userRepository.findById(new ObjectId(addUserFollowingRequest.getUserId()))
                .orElseThrow(() -> new UserNotFoundException(currentUserId));
        var currentUserToSave = userCreator.createWithNewFollowingUser(currentUser, userToFollow);
        var savedUser = userRepository.save(currentUserToSave);
        return addUserFollowingResponseMapper.map(savedUser);
    }
}
