package com.example.demo;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private CartRepository cartRepository = Mockito.mock(CartRepository.class);
    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    private ModifyCartRequest cartRequest;


    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

        cartRequest = TestObjectUtils.cgetCrtRequest();
        User user = TestObjectUtils.getUser();
        Item item = TestObjectUtils.getItem();
        Cart cart = user.getCart();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByUsername("udacity")).thenReturn(user);
        Mockito.when(itemRepository.findById(1l)).thenReturn(Optional.of(item));

        cart.setUser(user);
        Mockito.when(cartRepository.save(cart)).thenReturn(cart);
    }


    @Test
    public void addTocart() {
        ResponseEntity<Cart> cartResponseEntity = cartController.addTocart(cartRequest);
        Assert.assertNotNull(cartResponseEntity);
        Assert.assertEquals(HttpStatus.OK.value(), cartResponseEntity.getStatusCodeValue());
        Mockito.verify(userRepository).findByUsername("udacity");

        cartRequest.setUsername("null");
        cartResponseEntity = cartController.addTocart(cartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), cartResponseEntity.getStatusCodeValue());
    }

    @Test
    public void removeFromcart() {
        ResponseEntity<Cart> cartResponseEntity = cartController.removeFromcart(cartRequest);
        Assert.assertNotNull(cartResponseEntity);
        Assert.assertEquals(HttpStatus.OK.value(), cartResponseEntity.getStatusCodeValue());
        Mockito.verify(userRepository).findByUsername("udacity");

        cartRequest.setUsername("null");
        cartResponseEntity = cartController.removeFromcart(cartRequest);
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), cartResponseEntity.getStatusCodeValue());
    }
}
