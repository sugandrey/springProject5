package com.example.services;

import com.example.models.Item;
import com.example.models.Person;
import com.example.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(final ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(final String itemName) {
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(final Person person) {
        return itemsRepository.findByOwner(person);
    }
}
