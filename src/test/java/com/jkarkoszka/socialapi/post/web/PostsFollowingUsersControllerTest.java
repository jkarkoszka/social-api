package com.jkarkoszka.socialapi.post.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkarkoszka.socialapi.helper.TestPostCreator;
import com.jkarkoszka.socialapi.helper.TestUserCreator;
import com.jkarkoszka.socialapi.post.repository.PostRepository;
import com.jkarkoszka.socialapi.user.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostsFollowingUsersControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    TestUserCreator testUserCreator;
    @Autowired
    TestPostCreator testPostCreator;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void shouldReturnPostOfFollowingUsers() throws Exception {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserId = new ObjectId("507f1f77bcf86cd799439012");
        var secondFollowingUserId = new ObjectId("507f1f77bcf86cd799439013");
        var thirdFollowingUserId = new ObjectId("507f1f77bcf86cd799439014");
        userRepository.save(testUserCreator.create(firstFollowingUserId));
        userRepository.save(testUserCreator.create(secondFollowingUserId));
        userRepository.save(testUserCreator.create(thirdFollowingUserId));
        userRepository.save(testUserCreator.create(currentUserId, Set.of(firstFollowingUserId, secondFollowingUserId, thirdFollowingUserId)));
        var firstFollowingUserMessage = "first following post";
        var secondFollowingUserMessage = "second following post";
        var thirdFollowingUserMessage = "third following post";
        var fourthFollowingUserMessage = "third following post";
        postRepository.save(testPostCreator.create(firstFollowingUserMessage, firstFollowingUserId, new Date(156698112013L)));
        postRepository.save(testPostCreator.create(secondFollowingUserMessage, secondFollowingUserId, new Date(156698112014L)));
        postRepository.save(testPostCreator.create(thirdFollowingUserMessage, thirdFollowingUserId, new Date(156698112015L)));
        postRepository.save(testPostCreator.create(fourthFollowingUserMessage, secondFollowingUserId, new Date(156698112016L)));

        //when
        var result = mvc.perform(get("/posts/followingUsers")
                .header("X-Current-User-Id", currentUserId));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements", is(4)))
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].message", is(fourthFollowingUserMessage)))
                .andExpect(jsonPath("$.content[0].userId", is(secondFollowingUserId.toHexString())))
                .andExpect(jsonPath("$.content[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.content[1].id").isNotEmpty())
                .andExpect(jsonPath("$.content[1].message", is(thirdFollowingUserMessage)))
                .andExpect(jsonPath("$.content[1].userId", is(thirdFollowingUserId.toHexString())))
                .andExpect(jsonPath("$.content[1].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.content[2].id").isNotEmpty())
                .andExpect(jsonPath("$.content[2].message", is(secondFollowingUserMessage)))
                .andExpect(jsonPath("$.content[2].userId", is(secondFollowingUserId.toHexString())))
                .andExpect(jsonPath("$.content[2].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.content[3].message", is(firstFollowingUserMessage)))
                .andExpect(jsonPath("$.content[3].userId", is(firstFollowingUserId.toHexString())))
                .andExpect(jsonPath("$.content[3].createdDate").isNotEmpty());
    }

    @Test
    public void shouldReturnNoPostOfFollowingUsersIfThereAreNoPosts() throws Exception {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserId = new ObjectId("507f1f77bcf86cd799439012");
        var secondFollowingUserId = new ObjectId("507f1f77bcf86cd799439013");
        var thirdFollowingUserId = new ObjectId("507f1f77bcf86cd799439014");
        userRepository.save(testUserCreator.create(firstFollowingUserId));
        userRepository.save(testUserCreator.create(secondFollowingUserId));
        userRepository.save(testUserCreator.create(thirdFollowingUserId));
        userRepository.save(testUserCreator.create(currentUserId, Set.of(firstFollowingUserId, secondFollowingUserId, thirdFollowingUserId)));
        postRepository.save(testPostCreator.create("message", currentUserId, new Date(156698112013L)));

        //when
        var result = mvc.perform(get("/posts/followingUsers")
                .header("X-Current-User-Id", currentUserId));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements", is(0)));
    }

    @Test
    public void shouldReturnNoPostOfFollowingUsersIfThereAreNoFollowingUsers() throws Exception {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var firstFollowingUserId = new ObjectId("507f1f77bcf86cd799439012");
        var secondFollowingUserId = new ObjectId("507f1f77bcf86cd799439013");
        var thirdFollowingUserId = new ObjectId("507f1f77bcf86cd799439014");
        userRepository.save(testUserCreator.create(firstFollowingUserId));
        userRepository.save(testUserCreator.create(secondFollowingUserId));
        userRepository.save(testUserCreator.create(thirdFollowingUserId));
        userRepository.save(testUserCreator.create(currentUserId, Set.of()));
        postRepository.save(testPostCreator.create("message", currentUserId, new Date(156698112014L)));
        postRepository.save(testPostCreator.create("message1", secondFollowingUserId, new Date(156698112015L)));
        postRepository.save(testPostCreator.create("message2", thirdFollowingUserId, new Date(156698112016L)));
        postRepository.save(testPostCreator.create("message3", thirdFollowingUserId, new Date(156698112017L)));

        //when
        var result = mvc.perform(get("/posts/followingUsers")
                .header("X-Current-User-Id", currentUserId));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements", is(0)));
    }
}
