package com.example.ooadgroupproject.service;

import com.alipay.api.AlipayApiException;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    Result queryBillData(Date date)throws AlipayApiException;

    List<ItemsShoppingRecord>findByItemNameAndUserMail(String itemName, String userMail);

    ItemsShoppingRecord getItemShoppingRecord(long id) throws JsonProcessingException;

    Result alipayRefund(long id) throws AlipayApiException, JsonProcessingException;

    Result checkPayStatus(String itemShoppingRecordId) throws JsonProcessingException;

    Result alipayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception;

    Result alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
