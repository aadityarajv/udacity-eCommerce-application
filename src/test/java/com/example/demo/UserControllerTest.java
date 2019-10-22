package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerTest {

    @Mock
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Before
    public void setUp() {
    }

    //@Test
    public void createUser() {

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("manisht");
        userRequest.setPassword("manisht");
        userRequest.setConfirmPassword("manisht");

        ResponseEntity<User> responseEntity = userController.createUser(userRequest);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCodeValue());

        User user = responseEntity.getBody();
    }
}
