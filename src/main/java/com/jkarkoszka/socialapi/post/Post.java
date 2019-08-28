package com.jkarkoszka.socialapi.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@AllArgsConstructor
@Getter
@Builder
public class Post {

    @Id
    private final ObjectId id;
    private final String message;
    private final ObjectId userId;
    private Date createdDate;
}