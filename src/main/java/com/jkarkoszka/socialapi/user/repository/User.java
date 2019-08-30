package com.jkarkoszka.socialapi.user.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    private final ObjectId id;
    private final Set<ObjectId> followingUsers;
}
