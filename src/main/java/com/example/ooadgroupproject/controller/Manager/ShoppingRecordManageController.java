package com.example.ooadgroupproject.controller.Manager;

import com.alipay.api.AlipayClient;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManageShoppingRecord")
public class ShoppingRecordManageController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    @RequestMapping("/findShoppingRecordByItemName")
    public Result findShoppingRecordByItemName(String itemName){
        return Result.success(itemsShoppingRecordService.findByItemName(itemName));
    }
    @RequestMapping("/findShoppingRecordByUserMail")
    public Result findShoppingRecordByUserMail(String userMail){
        return Result.success(itemsShoppingRecordService.findByUserMail(userMail));
    }

    //去支付宝查交易账单
    @RequestMapping("/alipayDataBillAccountLogQuery")
    public Result alipayDataBillAccountLogQuery(String tradeNo){
        return Result.success();
    }

}
