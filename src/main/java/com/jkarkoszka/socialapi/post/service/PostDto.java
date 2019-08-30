package com.jkarkoszka.socialapi.post.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
public class PostDto {

    private final String id;
    private final String message;
    private final String userId;
    private final Date createdDate;
}
