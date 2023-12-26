package com.example.ooadgroupproject.service;

import com.alipay.api.AlipayApiException;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ItemsShoppingRecordService {
    public List<ItemsShoppingRecord>findAll();

    Optional<ItemsShoppingRecord> findById(long id);

    public List<ItemsShoppingRecord>findByUserMail(String userMail);
    public List<ItemsShoppingRecord>findByItemName(String itemName);
    public String callAlipayToPurchase(ItemsShoppingRecord itemsShoppingRecord) throws AlipayApiException;
    public void save(ItemsShoppingRecord itemsShoppingRecord);
    String queryAlipayStatus(long tradeNo) throws AlipayApiException;

    String queryBillData(Date date)throws AlipayApiException;
}
