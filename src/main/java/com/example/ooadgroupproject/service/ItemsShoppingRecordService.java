package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.entity.ItemsShoppingRecord;

import java.util.List;

public interface ItemsShoppingRecordService {
    public List<ItemsShoppingRecord>findAll();
    public List<ItemsShoppingRecord>findByUserMail(String userMail);
    public List<ItemsShoppingRecord>findByItemName(String itemName);
}
