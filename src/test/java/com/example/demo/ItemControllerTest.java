package com.example.demo;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);


    @BeforeEach
    public void setUp() {

        Item item = new Item();
        item.setId(1l);
        item.setName("item");
        item.setDescription("description");
        item.setPrice(new BigDecimal(101));

        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.findByName("item")).thenReturn(Arrays.asList(item));
        Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(item));
    }


    @Test
    public void getItems() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItems();
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Assertions.assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    public void getItemById() {
        ResponseEntity<Item> responseEntity = itemController.getItemById(1l);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    public void getItemsByName() {
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Assertions.assertEquals(1, responseEntity.getBody().size());
    }
}
