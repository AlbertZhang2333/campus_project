package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.ItemsRepository;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;
    @Override
    public List<Item> findAll(){
        return itemsRepository.findAll();
    }

    @Override
    public Optional<Item> findByName(String name) {
        return itemsRepository.findByName(name);
    }

    @Override
    public void addItem(Item item, int addNum) {
        item.addNum(addNum);
    }

    @Override
    public boolean reduceItem(Item item, int num) {
        return item.shoppingItem(num);
    }

    @Override
    public double calculatePrice(Item item, int num) {
        return item.calculateTotalPrice(num);
    }



}
