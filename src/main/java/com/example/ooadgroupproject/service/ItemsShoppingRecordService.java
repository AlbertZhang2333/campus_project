package com.example.ooadgroupproject.service;

import com.alipay.api.AlipayApiException;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;

import java.util.List;

public interface ItemsShoppingRecordService {
    public List<ItemsShoppingRecord>findAll();
    public List<ItemsShoppingRecord>findByUserMail(String userMail);
    public List<ItemsShoppingRecord>findByItemName(String itemName);
    public boolean callAlipayToPurchase(String userMail, Item item,int num) throws AlipayApiException;
}
