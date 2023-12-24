package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.Result;
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
    public boolean addItem(String name, int addNum) {
        Item item = itemsRepository.findByName(name).orElse(null);
        if (item != null) {
            item.addNum(addNum);
            itemsRepository.save(item);
        }
        return false;
    }

    @Override
    public boolean reduceItem(String name, int num) {
        Item item = itemsRepository.findByName(name).orElse(null);
        if(item == null){
            return false;
        }
        return item.shoppingItem(num);
    }

    @Override
    public double calculatePrice(Item item, int num) {
        return item.calculateTotalPrice(num);
    }

    @Override
    public Result generateANewItem(String name, int num, double price, String description, String imagePath) throws Exception {
        if(itemsRepository.findByName(name).isPresent()){
            return Result.fail("该名称的商品已存在");
        }
        Item item=Item.generateNewItems(name,num,price,description,imagePath);
        itemsRepository.save(item);
        return Result.success("目标商品已创建");
    }



}
