package com.zoryn.java.store.controller;

import com.google.common.collect.ImmutableList;
import com.zoryn.java.store.model.Item;
import com.zoryn.java.store.repository.ItemRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

  @Autowired
  private ItemRepository itemRepository;

  ItemController() {
  }

  @GetMapping("/items")
  ImmutableList<Item> getAllItems() {
    return ImmutableList.copyOf(itemRepository.findAll());
  }

  @GetMapping("/items/{id}")
  Item getItem(@PathVariable Long id) {
    return itemRepository.findById(id).orElseThrow();
  }
}
