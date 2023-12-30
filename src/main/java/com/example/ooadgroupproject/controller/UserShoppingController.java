package com.example.ooadgroupproject.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.ooadgroupproject.common.CartForm;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.PayTool;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ItemsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;

import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/UserShopping")
public class UserShoppingController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private CacheClient cacheClient;

    private final Logger logger=Logger.getLogger(UserShoppingController.class);
    //TODO
    //用于拉取支付宝交易页面，返回一个html信息，需要和前端协同才能完成该功能
    @PutMapping("/purchase")
    public Result purchase(@RequestParam String itemName,@RequestParam Integer num){
        Account account=LoginUserInfo.getAccount();
        try {
            Item item=itemsService.findByName(itemName);
            if(item==null){
                return Result.fail(new String[]{"不存在该商品！可能是系统故障或管理员临时进行了调整"});
            }
            ItemsShoppingRecord itemsShoppingRecord=new ItemsShoppingRecord(item,num,account.getUserMail());

            String res=itemsShoppingRecordService.callAlipayToPurchase(itemsShoppingRecord);
            //接下来同步通知调用，询问支付宝用户是否已经支付
            if(res!=null){
                return Result.success(new String[]{String.valueOf(itemsShoppingRecord.getId()),res});
            }else{
                return Result.fail(new String[]{"支付宝支付故障，请核对你的资金并与联系管理员解决！"});
            }
        }catch (AlipayApiException e){
            return Result.fail(new String[]{"支付宝支付故障，请核对你的资金并与联系管理员解决！"});
        }
    }
    @GetMapping("/checkIfUserHasPay")
    public Result checkIfUserHasPay(@RequestParam String itemShoppingRecordId){
        return Result.success("成功支付");
//        return itemsShoppingRecordService.checkPayStatus(itemShoppingRecordId);
    }

    @GetMapping("/AliPayReturn")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response){
        try {
            Result result= itemsShoppingRecordService.alipayReturn(request, response);
            if(result.isIfSuccess()){
                return "success";
            }else{
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }
    @PostMapping("/AlipayNotify")
    public String notifyUrl(HttpServletRequest request,HttpServletResponse response){
        try {
            Result result=itemsShoppingRecordService.alipayNotify(request,response);
            if(result.isIfSuccess()){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }


    @GetMapping("/checkItemCart")
    public Result checkItemCart(){
        Account account=LoginUserInfo.getAccount();
        List<CartForm>cartFormList=cacheClient.getCartForms(account.getUserMail());
        if(cartFormList==null|| cartFormList.isEmpty()){
            cartFormList=new ArrayList<>();
        }
        return Result.success(cartFormList);
    }

    @PostMapping("/addItemToTheCart")
    public Result addItemToTheCart(String itemName, Integer num) {
        Account account=LoginUserInfo.getAccount();
        Item item=itemsService.findByName(itemName);
        if(item==null){
            return Result.fail("不存在该商品！可能是系统故障或管理员临时进行了调整");
        }
        CartForm cartForm=new CartForm(itemName,num,account.getUserMail());
        cacheClient.setItemsShoppingCart(account.getUserMail(),cartForm);
        return Result.success(cartForm);
    }
    @DeleteMapping("/deleteItemFromTheCart")
    public Result deleteItemFromTheCart(long cartFormTime){
        Account account=LoginUserInfo.getAccount();
        if(cacheClient.deleteItemsShoppingCart(account.getUserMail(),cartFormTime)){
            return Result.success("已将目标商品从购物车中删除");
        }else{
            return Result.fail("删除失败，可能是因为该商品不存在于购物车中");
        }
    }

    @GetMapping("/checkShoppingItems")
    public Result checkShoppingItems(){
        return Result.success(itemsService.findAll());
    }

    @GetMapping("/searchItem")
    public Result searchItem(String itemName){
        Item item=itemsService.findByName(itemName);
        if(item==null){
            return Result.fail("不存在该商品！可能是系统故障或管理员临时进行了调整");
        }
        return Result.success(item);
    }

    @GetMapping("/checkShoppingRecords")
    public Result checkShoppingRecords(){
        Account account=LoginUserInfo.getAccount();
        List<ItemsShoppingRecord>itemsShoppingRecordList=itemsShoppingRecordService.findByUserMail(account.getUserMail());
        if(itemsShoppingRecordList==null|| itemsShoppingRecordList.isEmpty()){
            itemsShoppingRecordList=new ArrayList<>();
        }
        return Result.success(itemsShoppingRecordList);
    }
    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(itemsService.findAll());
    }


}
