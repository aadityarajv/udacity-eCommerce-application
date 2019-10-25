package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.util.Arrays;

public class TestObjectUtils {

    public static User getUser() {
        User user = new User();
        user.setId(1l);
        user.setUsername("udacity");
        user.setPassword("udacity-password");

        Item item = new Item();
        item.setId(11l);
        item.setName("item-001");
        item.setPrice(new BigDecimal(101));
        item.setDescription("item-001");

        Cart cart = new Cart();
        cart.setId(2l);
        cart.setTotal(new BigDecimal(101));
        cart.setItems(Arrays.asList(item));

        user.setCart(cart);
        return user;
    }

    public static Item getItem() {
        Item item = new Item();
        item.setId(1l);
        item.setName("phone");
        item.setPrice(new BigDecimal(101));
        item.setDescription("New flagship Phone.");
        return item;
    }

    public static ModifyCartRequest cgetCrtRequest() {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setQuantity(1);
        cartRequest.setUsername("udacity");
        cartRequest.setItemId(1l);
        return cartRequest;
    }
}
