package com.zoryn.itemservice.controller;

import com.google.common.collect.ImmutableList;
import com.zoryn.itemservice.model.Item;
import com.zoryn.itemservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {
  private final ItemRepository itemRepository;

  @Autowired
  ItemController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @GetMapping
  ImmutableList<Item> getAllItems() {
    return ImmutableList.copyOf(itemRepository.findAll());
  }

  @GetMapping("{id}")
  Item getItem(@PathVariable Long id) {
    return itemRepository.findById(id).orElseThrow();
  }
}
