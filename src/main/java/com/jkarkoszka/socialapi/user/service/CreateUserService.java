package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final UserCreator userCreator;
    private final UserDtoMapper userDtoMapper;

    public UserDto createIfNotExist(ObjectId id) {
        var user = userRepository.findById(id)
                .orElseGet(() -> {
                    var newUser = userCreator.createEmpty(id);
                    return userRepository.save(newUser);
                });
        return userDtoMapper.map(user);
    }
}
