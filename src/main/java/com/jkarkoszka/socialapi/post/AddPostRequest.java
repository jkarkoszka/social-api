package com.jkarkoszka.socialapi.post;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
public class AddPostRequest {

    @NotBlank
    @Size(max = 140)
    private final String message;
}
