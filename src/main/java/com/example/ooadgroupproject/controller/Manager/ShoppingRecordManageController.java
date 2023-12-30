package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManageShoppingRecord")
public class ShoppingRecordManageController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;

    private final Logger logger=Logger.getLogger(this.getClass());
    @GetMapping("/findShoppingRecordByItemName")
    public Result findShoppingRecordByItemName(String itemName){
        return Result.success(itemsShoppingRecordService.findByItemName(itemName));
    }
    @GetMapping("/findShoppingRecordByUserMail")
    public Result findShoppingRecordByUserMail(String userMail){
        return Result.success(itemsShoppingRecordService.findByUserMail(userMail));
    }
    @GetMapping("/getShoppingRecordById")
    public Result getShoppingRecordById(long id){
        try {
            return Result.success(itemsShoppingRecordService.getItemShoppingRecord(id));
        }catch (Exception e){
            logger.error(e.getMessage()+" 异常id:"+id);
            return Result.fail("查询出现异常");
        }
    }


    //去支付宝查交易账单
    @RequestMapping("/alipayDataBillAccountLogQuery")
    public Result alipayDataBillAccountLogQuery(String tradeNo){
        return Result.success();
    }

}
