package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemsService {
    public List<Item> findAll();
    public Item findByName(String name);

    Result updateItem(String itemName, Double price,
                      String description, String imagePath);

    public boolean addItem(String name, Integer addNum);
    public boolean reduceItem(String name, Integer num);
    public Double calculatePrice(Item item,Integer num);

    public Result generateANewItem(String name,Integer num,Double price,String description,String imagePath) throws Exception;


}
