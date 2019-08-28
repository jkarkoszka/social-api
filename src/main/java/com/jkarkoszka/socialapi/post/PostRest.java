package com.jkarkoszka.socialapi.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@Getter
@Builder
public class PostRest {

    @Id
    private final String id;
    private final String message;
    private final String userId;
    private final Date createdDate;
}
