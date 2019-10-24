package com.example.demo;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static CreateUserRequest createUserRequest;
    private static User userInput;

    @BeforeAll
    public static void setUp() throws Exception {

        createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("udacity");
        createUserRequest.setPassword("udacity12345");
        createUserRequest.setConfirmPassword("udacity12345");

        userInput = new User();
        userInput.setUsername("udacity");
        userInput.setPassword("udacity12345");
    }

    @Test
    public void getItems() {

        String getUserUrl = "http://localhost:" + port + "/api/items/";
        ResponseEntity<User> entity = restTemplate.getForEntity(getUserUrl, User.class);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), entity.getStatusCodeValue());
    }

    @Test
    public void createUser() {

        String createUserUrl = "http://localhost:" + port + "/api/user/create";
        User user = restTemplate.postForObject(createUserUrl, createUserRequest, User.class);

        Assertions.assertNotNull(user);
    }

    //@Test
    public void login() {

        String createUserUrl = "http://localhost:" + port + "/api/user/create";
        User user = restTemplate.postForObject(createUserUrl, createUserRequest, User.class);
        Assertions.assertNotNull(user);

        String loginUrl = "http://localhost:" + port + "/login";
        User logInUser = restTemplate.postForObject(loginUrl, userInput, User.class);
        Assertions.assertNotNull(logInUser);
    }
}
