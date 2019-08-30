package com.jkarkoszka.socialapi.user.service;

import com.jkarkoszka.socialapi.user.repository.User;
import org.springframework.stereotype.Component;

@Component
class UserDtoMapper {

    UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .followingUsers(user.getFollowingUsers())
                .build();
    }
}
