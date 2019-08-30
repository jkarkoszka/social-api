package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserDto findUser(ObjectId id) {
        return userRepository.findById(id)
                .map(userDtoMapper::map)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
