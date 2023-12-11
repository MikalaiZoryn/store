package com.zoryn.java.store.repository;

import com.zoryn.java.store.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
