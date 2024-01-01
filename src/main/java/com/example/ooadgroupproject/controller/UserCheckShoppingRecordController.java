package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/UserCheckShoppingRecord")
public class UserCheckShoppingRecordController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    private final Logger logger=Logger.getLogger(this.getClass());
    @GetMapping("/UserCheckSelfShoppingRecord")
    public Result selfShoppingRecord(){
        Account account= LoginUserInfo.getAccount();
        return Result.success(itemsShoppingRecordService.findByUserMail(account.getUserMail()));
    }

    @GetMapping("/UserCheckShoppingRecordByItemName")
    public Result shoppingRecordByItemName(@RequestParam String itemName){
        Account account= LoginUserInfo.getAccount();
        return Result.success(itemsShoppingRecordService.findByItemNameAndUserMail(itemName,account.getUserMail()));
    }

    @GetMapping("/UserCheckShoppingRecordById")
    public Result shoppingRecordById(@RequestParam long id){
        try {
            ItemsShoppingRecord itemsShoppingRecord = itemsShoppingRecordService.getItemShoppingRecord(id);
            Account account=LoginUserInfo.getAccount();
            if(itemsShoppingRecord==null){
                return Result.fail("不存在该单号");
            }
            if(account.getUserMail().equals(itemsShoppingRecord.getUserMail())){
                return Result.success(itemsShoppingRecord);
            }else {
                return Result.fail("不存在该单号");
            }
        }catch (Exception e){
            logger.error(e+"用户在查询销售记录"+id+"时发生异常");
            return Result.fail("查询异常");
        }
    }



}
