package com.jkarkoszka.socialapi.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkarkoszka.socialapi.helper.TestPostCreator;
import com.jkarkoszka.socialapi.helper.TestUserCreator;
import com.jkarkoszka.socialapi.user.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostsMyControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    TestPostCreator testPostCreator;
    @Autowired
    TestUserCreator testUserCreator;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void shouldReturnMyLastPosts() throws Exception {
        //given
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var otherUserId = new ObjectId("507f1f77bcf86cd799439012");
        var firstMessage = "message1";
        var secondMessage = "message2";
        var thirdMessage = "message3";
        userRepository.save(testUserCreator.create(currentUserId));
        userRepository.save(testUserCreator.create(otherUserId));
        postRepository.save(testPostCreator.create(firstMessage, currentUserId, new Date(156698112013L)));
        postRepository.save(testPostCreator.create(secondMessage, currentUserId, new Date(156698112014L)));
        postRepository.save(testPostCreator.create(thirdMessage, currentUserId, new Date(156698112015L)));
        postRepository.save(testPostCreator.create("message4", otherUserId, new Date(156698112016L)));
        postRepository.save(testPostCreator.create("message5", otherUserId, new Date(156698112017L)));

        //when
        var result = mvc.perform(get("/posts/my")
                .header("X-Current-User-Id", currentUserId));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements", is(3)))
                .andExpect(jsonPath("$.content[0].id").isNotEmpty())
                .andExpect(jsonPath("$.content[0].message", is(thirdMessage)))
                .andExpect(jsonPath("$.content[0].userId", is(currentUserId.toHexString())))
                .andExpect(jsonPath("$.content[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.content[1].id").isNotEmpty())
                .andExpect(jsonPath("$.content[1].message", is(secondMessage)))
                .andExpect(jsonPath("$.content[1].userId", is(currentUserId.toHexString())))
                .andExpect(jsonPath("$.content[1].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.content[2].id").isNotEmpty())
                .andExpect(jsonPath("$.content[2].message", is(firstMessage)))
                .andExpect(jsonPath("$.content[2].userId", is(currentUserId.toHexString())))
                .andExpect(jsonPath("$.content[2].createdDate").isNotEmpty());
    }
}
