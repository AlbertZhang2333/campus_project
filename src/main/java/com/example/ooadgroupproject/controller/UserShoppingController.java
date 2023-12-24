package com.example.ooadgroupproject.controller;

import com.alipay.api.AlipayApiException;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;

import java.util.List;

@RestController
@RequestMapping("/UserShopping")
public class UserShoppingController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private CacheClient cacheClient;

    //TODO
    //用于拉取支付宝交易页面，返回一个html信息，需要和前端协同才能完成该功能
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

    //TODO
    //用户将商品拉入到购物车中，购物车内单个商品仅仅只能存在一天
//    @PutMapping("/addItemToTheCart")
//    public Result addItemToTheCart(String itemName, int num){
//        Account account=LoginUserInfo.getAccount();
//        Item item=itemsService.findByName(itemName).orElse(null);
//        if(item==null){
//            return Result.fail("不存在该商品！可能是系统故障或管理员临时进行了调整");
//        }
//        ItemsShoppingRecord itemsShoppingRecord=new ItemsShoppingRecord(item,num,account.getUserMail());
//        itemsShoppingRecordService.save(itemsShoppingRecord);
//        cacheClient.setItemsShoppingCart(account.getUserMail(),itemsShoppingRecord);
//        return Result.success("已将目标商品添加入购物车");
//    }
//    @PutMapping("/deleteItemFromTheCart")
//    public Result deleteItemFromTheCart(long itemShoppingRecord_id){
//        Account account=LoginUserInfo.getAccount();
//        if(cacheClient.deleteItemsShoppingCart(account.getUserMail(),itemShoppingRecord_id)){
//            return Result.success("已将目标商品从购物车中删除");
//        }else{
//            return Result.fail("删除失败，可能是因为该商品不存在于购物车中");
//        }
//    }
//
//    @PutMapping("/checkItemCart")
//    public Result checkItemCart(){
//        Account account=LoginUserInfo.getAccount();
//        List<ItemsShoppingRecord> itemsShoppingRecordList=cacheClient.getItemsShoppingCart(account.getUserMail());
//        if(itemsShoppingRecordList.size()==0){
//            return Result.fail("购物车中没有商品，请先添加商品");
//        }
//        double totalPrice=0;
//        for(ItemsShoppingRecord itemsShoppingRecord:itemsShoppingRecordList){
//
//        }
//    }






}
