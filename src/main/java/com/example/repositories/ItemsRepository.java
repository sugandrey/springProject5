package com.example.repositories;

import com.example.models.Item;
import com.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {

    List<Item> findByItemName(String itemName);
//person.getItems
    List<Item> findByOwner(Person owner);


}
