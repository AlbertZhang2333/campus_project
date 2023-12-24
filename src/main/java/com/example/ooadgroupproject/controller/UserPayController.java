package com.example.ooadgroupproject.controller;

import com.alipay.api.AlipayApiException;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.service.ItemsService;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
@RestController
@RequestMapping("/UserPay")
public class UserPayController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    @Autowired
    private ItemsService itemsService;

    //TODO
    @PutMapping("/purchase")
    public Result purchase(String itemName, int num){
        Account account=LoginUserInfo.getAccount();
        try {
            Item item=itemsService.findByName(itemName).orElse(null);
            if(item==null){
                return Result.fail("不存在该商品！可能是系统故障或管理员临时进行了调整");
            }
            Result res=itemsShoppingRecordService.callAlipayToPurchase(account.getUserMail(),item, num);
            if(res.isIfSuccess()){
                return res;
            }else{
                return Result.fail("支付失败！");
            }
        }catch (AlipayApiException e){
            return Result.fail("支付宝支付故障，请核对你的资金并与联系管理员解决！");
        }


    }

}
