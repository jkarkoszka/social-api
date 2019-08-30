package com.jkarkoszka.socialapi.user.service;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with id '%s' not found.";

    UserNotFoundException(ObjectId userId) {
        super(String.format(MESSAGE, userId));
    }
}
