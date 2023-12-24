package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemsService {
    public List<Item> findAll();
    public Optional<Item> findByName(String name);
    public boolean addItem(String name,int addNum);
    public boolean reduceItem(String name, int num);
    public double calculatePrice(Item item,int num);

    public Result generateANewItem(String name,int num,double price,String description,String imagePath) throws Exception;

}
