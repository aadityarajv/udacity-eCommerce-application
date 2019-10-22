package com.example.demo;

import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);


    @Before
    public void setUp() {

        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

        User user = TestObjectUtils.getUser();
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByUsername("udacity")).thenReturn(user);
        Mockito.when(orderRepository.findByUser(user)).thenReturn(Collections.emptyList());
        Mockito.when(orderRepository.save(UserOrder.createFromCart(user.getCart()))).thenReturn(new UserOrder());
    }

    @Test
    public void submit() {
        ResponseEntity<UserOrder> responseEntity = orderController.submit("udacity");
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

        responseEntity = orderController.submit("null");
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUser() {
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("udacity");
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

        responseEntity = orderController.getOrdersForUser("null");
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue());
    }
}
