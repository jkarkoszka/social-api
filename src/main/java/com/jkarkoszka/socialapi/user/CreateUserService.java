package com.jkarkoszka.socialapi.user;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final UserCreator userCreator;

    public User createIfNotExist(ObjectId id) {
        return userRepository.findById(id)
                .orElseGet(() -> {
                    var user = userCreator.createEmpty(id);
                    return userRepository.save(user);
                });
    }
}
