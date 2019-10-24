package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    private User user;


    @BeforeEach
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

        user = new User();
        user.setId(1l);
        user.setUsername("udacity");
        user.setPassword("udacity-password");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByUsername("udacity")).thenReturn(user);
    }


    @Test
    public void createUser() {

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("udacity");
        userRequest.setPassword("udacity");
        userRequest.setConfirmPassword("udacity");

        ResponseEntity<User> responseEntity = userController.createUser(userRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

        userRequest.setPassword("udacity-password");
        userRequest.setConfirmPassword("udacity-password-diff");
        responseEntity = userController.createUser(userRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());

        userRequest.setPassword("udacity-password");
        userRequest.setConfirmPassword("udacity-password");
        responseEntity = userController.createUser(userRequest);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        User user = responseEntity.getBody();
        Assertions.assertEquals(user.getUsername(), userRequest.getUsername());
    }

    @Test
    public void findById() {
        ResponseEntity<User> responseEntity = userController.findById(1L);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        User user = responseEntity.getBody();
        Assertions.assertEquals(user.getUsername(), user.getUsername());
    }

    @Test
    public void findByUserName() {
        ResponseEntity<User> responseEntity = userController.findByUserName(user.getUsername());
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        User user = responseEntity.getBody();
        Assertions.assertEquals(user.getUsername(), user.getUsername());
    }
}
