package com.jkarkoszka.socialapi.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkarkoszka.socialapi.helper.TestUserCreator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsersMeFollowingControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestUserCreator testUserCreator;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldAddNewFollowingUser() throws Exception {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var followingUserIdAsHexString = "507f1f77bcf86cd799439012";
        var followingUserId = new ObjectId(followingUserIdAsHexString);
        var newFollowingUserIdAsHexString = "507f1f77bcf86cd799439013";
        var newFollowingUserId = new ObjectId(newFollowingUserIdAsHexString);
        userRepository.save(testUserCreator.create(currentUserId, Set.of(followingUserId)));
        userRepository.save(testUserCreator.create(followingUserId, Set.of()));
        userRepository.save(testUserCreator.create(newFollowingUserId, Set.of()));
        AddUserFollowingRequest addUserFollowingRequest = new AddUserFollowingRequest(newFollowingUserIdAsHexString);

        //when
        var result = mvc.perform(post("/users/me/followingUsers")
                .header("X-Current-User-Id", currentUserId)
                .content(objectMapper.writeValueAsString(addUserFollowingRequest))
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.followingUsers[0]", is(followingUserIdAsHexString)))
                .andExpect(jsonPath("$.followingUsers[1]", is(newFollowingUserIdAsHexString)));
        assertThat(userRepository.findById(currentUserId).isPresent()).isTrue();
    }
}
