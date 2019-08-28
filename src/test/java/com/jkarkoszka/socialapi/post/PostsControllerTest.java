package com.jkarkoszka.socialapi.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkarkoszka.socialapi.helper.TestPostCreator;
import com.jkarkoszka.socialapi.helper.TestUserCreator;
import com.jkarkoszka.socialapi.user.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostsControllerTest {

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
    public void shouldSavePost() throws Exception {
        var message = "message123";
        var addPostRequest = new AddPostRequest(message);
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");
        var otherUserId = new ObjectId("507f1f77bcf86cd799439012");
        var expectedPost = testPostCreator.create("message123", currentUserId);
        testUserCreator.create(otherUserId);
        postRepository.save(testPostCreator.create("message1", otherUserId));
        postRepository.save(testPostCreator.create("message2", otherUserId));

        //when
        var result = mvc.perform(post("/posts")
                .header("X-Current-User-Id", currentUserId)
                .content(objectMapper.writeValueAsString(addPostRequest))
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty());
        assertThat(userRepository.findById(currentUserId).isPresent()).isTrue();
        assertThat(postRepository.findByUserIdOrderByCreatedDateDesc(otherUserId, PageRequest.of(0, 10)))
                .hasSize(2);
        var userPosts = postRepository.findByUserIdOrderByCreatedDateDesc(currentUserId, PageRequest.of(0, 10));
        assertThat(userPosts).hasSize(1);
        assertThat(userPosts.getContent().get(0)).isEqualToIgnoringGivenFields(expectedPost, "id", "createdDate");
    }

    @Test
    public void shouldReturnBadRequestIfMessageIsTooLong() throws Exception {
        var message = RandomStringUtils.randomAlphabetic(141);
        var addPostRequest = new AddPostRequest(message);
        var currentUserId = new ObjectId("507f1f77bcf86cd799439011");

        //when
        var result = mvc.perform(post("/posts")
                .header("X-Current-User-Id", currentUserId)
                .content(objectMapper.writeValueAsString(addPostRequest))
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
    }
}
