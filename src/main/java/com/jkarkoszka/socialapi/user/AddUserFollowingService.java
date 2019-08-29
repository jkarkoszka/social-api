package com.jkarkoszka.socialapi.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserFollowingService {

    private final FindUserService findUserService;
    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final AddUserFollowingResponseMapper addUserFollowingResponseMapper;

    public AddUserFollowingResponse addUserFollowing(ObjectId currentUserId, AddUserFollowingRequest addUserFollowingRequest) {
        var currentUser = findUserService.findUser(currentUserId);
        var userToFollow = findUserService.findUser(new ObjectId(addUserFollowingRequest.getUserId()));
        var currentUserToSave = userCreator.createWithNewFollowingUser(currentUser, userToFollow);
        var savedUser = userRepository.save(currentUserToSave);
        return addUserFollowingResponseMapper.map(savedUser);
    }
}
