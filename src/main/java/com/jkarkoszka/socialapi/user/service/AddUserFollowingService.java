package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddUserFollowingService {

    private final FindUserService findUserService;
    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final UserDtoMapper userDtoMapper;

    public UserDto addUserFollowing(ObjectId currentUserId, AddUserFollowingServiceRequest addUserFollowingServiceRequest) {
        var currentUserDto = findUserService.findUser(currentUserId);
        var userDtoToFollow = findUserService.findUser(new ObjectId(addUserFollowingServiceRequest.getUserId()));
        var currentUserToSave = userCreator.createWithNewFollowingUser(currentUserDto, userDtoToFollow);
        var savedUpdatedCurrentUser = userRepository.save(currentUserToSave);
        return userDtoMapper.map(savedUpdatedCurrentUser);
    }
}
