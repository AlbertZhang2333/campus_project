package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.dao.ItemsShoppingRecordRepository;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsShoppingRecordServiceImpl implements ItemsShoppingRecordService {
    @Autowired
    private ItemsShoppingRecordRepository itemsShoppingRecordRepository;

    @Override
    public List<ItemsShoppingRecord> findAll() {
        return itemsShoppingRecordRepository.findAll();
    }

    @Override
    public List<ItemsShoppingRecord> findByUserMail(String userMail) {
        return itemsShoppingRecordRepository.findByUserMail(userMail);
    }

    @Override
    public List<ItemsShoppingRecord> findByItemName(String itemName) {
        return itemsShoppingRecordRepository.findByItemName(itemName);
    }
}
