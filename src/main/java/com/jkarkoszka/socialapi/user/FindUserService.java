package com.jkarkoszka.socialapi.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;

    public User findUser(ObjectId id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
