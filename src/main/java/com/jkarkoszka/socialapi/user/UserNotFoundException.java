package com.jkarkoszka.socialapi.user;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public static final String MESSAGE = "User with id '%s' not found.";

    public UserNotFoundException(ObjectId userId) {
        super(String.format(MESSAGE, userId));
    }
}
