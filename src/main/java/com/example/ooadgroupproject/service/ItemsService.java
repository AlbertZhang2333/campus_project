package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemsService {
    public List<Item> findAll();
    public Optional<Item> findByName(String name);
    public void addItem(Item item,int addNum);
    public boolean reduceItem(Item item, int num);
    public double calculatePrice(Item item,int num);

}
