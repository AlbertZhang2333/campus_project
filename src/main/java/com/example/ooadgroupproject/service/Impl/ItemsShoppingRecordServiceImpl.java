package com.example.ooadgroupproject.service.Impl;

import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.example.ooadgroupproject.RedisLock;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.PayTool;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ItemsShoppingRecordRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ItemsService;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class ItemsShoppingRecordServiceImpl implements ItemsShoppingRecordService {
    @Autowired
    private ItemsShoppingRecordRepository itemsShoppingRecordRepository;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private PayTool payTool;
    @Autowired
    private CacheClient cacheClient;
    private final Logger logger=Logger.getLogger(ItemsShoppingRecordServiceImpl.class);

    @Override
    public List<ItemsShoppingRecord> findAll() {
        Sort sort=Sort.by(Sort.Direction.DESC,"id");
        return itemsShoppingRecordRepository.findAll(sort);
    }
    @Deprecated
    @Override
    public Optional<ItemsShoppingRecord> findById(long id) {
        return itemsShoppingRecordRepository.findById(id);
    }

    @Override
    public List<ItemsShoppingRecord> findByUserMail(String userMail) {
        return itemsShoppingRecordRepository.findByUserMail(userMail);
    }

    @Override
    public List<ItemsShoppingRecord> findByItemName(String itemName) {
        return itemsShoppingRecordRepository.findByItemName(itemName);
    }

    @Override
    public void save(ItemsShoppingRecord itemsShoppingRecord){
        cacheClient.addItemShoppingRecord(itemsShoppingRecord);
        itemsShoppingRecordRepository.save(itemsShoppingRecord);
    }
    @Override
    public List<ItemsShoppingRecord>findByItemNameAndUserMail(String itemName,String userMail){
        return itemsShoppingRecordRepository.findByItemNameAndUserMail(itemName,userMail);
    }
    @Override
    public ItemsShoppingRecord getItemShoppingRecord(long id) throws JsonProcessingException {
        String value=cacheClient.getItemShoppingRecord(id);
        if(value!=null){
            if(value.isEmpty()){
                return null;
            }else {
//                ObjectMapper objectMapper = new ObjectMapper();
//                ItemsShoppingRecord itemsShoppingRecord = objectMapper.readValue(value, ItemsShoppingRecord.class);
                ItemsShoppingRecord itemsShoppingRecord=ItemsShoppingRecord.getItemShoppingRecordFromJson(value);
                return itemsShoppingRecord;
            }
        }
        ItemsShoppingRecord tt= itemsShoppingRecordRepository.findById(id).orElse(null);
        if(tt!=null){
            cacheClient.addItemShoppingRecord(tt);
        }
        return tt;
    }
    //该方法用于请求支付宝完成金额交易，但是该方法不会完成减少库存的任务。减少库存的部分将在届时调用该方法的地方编写，以便于做高并发处理
    //暂时有些疑惑，我觉得可以加入查询状态功能，以应对更多场景
    //TODO
    //需要进一步优化！！
    @Override
    public String callAlipayToPurchase(ItemsShoppingRecord itemsShoppingRecord) throws AlipayApiException {
        //呼叫支付宝来结账
        if(!Objects.equals(itemsShoppingRecord.getStatus(), ItemsShoppingRecord.Initial_State)){
            return null;
        }
        AlipayClient alipayClient=payTool.getAlipayClient();
        //能成功建立联系，就存储对应的记录
        save(itemsShoppingRecord);
        itemsService.reduceItem(itemsShoppingRecord.getItemName(),itemsShoppingRecord.getNum());
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(PayTool.RETURN_URL);
        request.setNotifyUrl(PayTool.NOTIFY_URL);
        //设置支付宝的请求参数
        AlipayTradePagePayModel model=payTool.getPurchaseModel(itemsShoppingRecord);
        request.setBizModel(model);
        //调用支付宝的接口
        AlipayTradePagePayResponse response=alipayClient.pageExecute(request,"POST");
        String pageRedirectionData=response.getBody();
//        System.out.println(pageRedirectionData);
        //分两次存储，确保交易成功后，服务器即便突然崩溃，也能保证在用户支付成功前已将数据存储到数据库中
        if(response.isSuccess()) {
            //返回true，表示成功
            return pageRedirectionData;
        }else {
            //返回false，表示失败
            return null;
        }
    }

    @Override
    public String queryAlipayStatus(long tradeNo) throws AlipayApiException {
    AlipayClient alipayClient=payTool.getAlipayClient();
    AlipayTradeQueryRequest request=new AlipayTradeQueryRequest();
    AlipayTradeQueryModel model=new AlipayTradeQueryModel();
    model.setOutTradeNo(String.valueOf(tradeNo));
    request.setBizModel(model);
    AlipayTradeQueryResponse response=alipayClient.execute(request);
        if(response.isSuccess()){
            return response.getTradeStatus();
        }else {
            logger.warn(response.getBody());
            return response.getTradeStatus();
        }
    }

    @Override
    public Result queryBillData(Date date)throws AlipayApiException{
        AlipayClient alipayClient=payTool.getAlipayClient();
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillDate(date.toString());
        model.setBillType("trade");
        request.setBizModel(model);
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        return Result.success(response.getBillDownloadUrl());
    }
    @Override
    public Result alipayRefund(long id) throws AlipayApiException, JsonProcessingException {
        ItemsShoppingRecord itemsShoppingRecord=getItemShoppingRecord(id);
        if(itemsShoppingRecord==null){
            logger.error("用户在查询"+id+"的订单时，未找到该订单！");
            return Result.fail("不存在该商品！如您确定已支付，请立刻联系管理员协助解决问题！");
        }
        if(itemsShoppingRecord.getStatus()!=ItemsShoppingRecord.Paid_State){
            return Result.fail("该订单未支付或已超过退款时间，无法退款！");
        }
        String out_trade_no=String.valueOf(itemsShoppingRecord.getId());
        double refund_amount=itemsShoppingRecord.getAmount();
        AlipayClient client=payTool.getAlipayClient();
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent=new JSONObject();
        bizContent.put("out_trade_no",out_trade_no);
        bizContent.put("refund_amount",refund_amount);
        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = client.execute(request);
        if(response.isSuccess()){
            itemsShoppingRecord.setStatus(ItemsShoppingRecord.Refund_State);
            save(itemsShoppingRecord);
            return Result.success("退款成功！");
        }
        return Result.fail("退款失败！请检查您的输入,如确认无误请联系管理员");
    }

    @Override
    public Result checkPayStatus(String itemShoppingRecordId) throws JsonProcessingException {
        long recordId=-1;
        try {
            recordId = Long.parseLong(itemShoppingRecordId);
        }catch (Exception e){
            return Result.fail("参数错误！");
        }
        ItemsShoppingRecord itemsShoppingRecord=getItemShoppingRecord(recordId);
        if(itemsShoppingRecord==null){
            return Result.fail("不存在该商品！如您确定已支付，请立刻联系管理员协助解决问题！");
        }
        try {
            String payInfo = queryAlipayStatus(recordId);
            return Result.success(payInfo);
        }catch (Exception e){
            logger.error(e.getMessage()+" "+itemShoppingRecordId+" ");
            return Result.fail("支付宝访问异常");
        }
    }

    @Override
    public Result alipayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> params=new HashMap<>();
        Map<String,String[]>requestParams = request.getParameterMap();
        for(Iterator<String>iter=requestParams.keySet().iterator();iter.hasNext();){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        AlipayConfig alipayConfig= (new PayTool().getAliPayConfig());
        boolean signVerified = AlipaySignature.rsaCheckV1(params,alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        if(signVerified){
            logger.info("同步验证通过");
            String out_trade_no=new String(request.getParameter("out_trade_no").
                    getBytes("ISO-8859-1"),"utf-8");
            String trade_no = new String(request.getParameter("trade_no").
                    getBytes("ISO-8859-1"),"UTF-8");
            String total_amount=new String(request.getParameter("total_amount").
                    getBytes("ISO-8859-1"),"UTF-8");
            //开始核对详细信息是否一致：若一致则更新交易记录
            ItemsShoppingRecord itemsShoppingRecord=getItemShoppingRecord(Long.parseLong(out_trade_no));
            if(!(itemsShoppingRecord!=null&&itemsShoppingRecord.ifEquals(Long.parseLong(out_trade_no), Double.valueOf(total_amount)))){
                logger.error("同步通知订单信息不一致！");
                return Result.fail("订单信息不一致！");
            }
            logger.info("订单："+itemsShoppingRecord.getId()+"同步验证通过");
            return Result.success();
        }else {
            logger.info("验签失败！");
            return Result.fail("验签失败");
        }
    }




    //TODO做异步通知
    @Override
    public Result alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> params=new HashMap<>();
        Map<String,String[]>requestParams = request.getParameterMap();
        for(Iterator<String>iter=requestParams.keySet().iterator();iter.hasNext();){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        AlipayConfig alipayConfig= (new PayTool().getAliPayConfig());
        boolean signVerified = AlipaySignature.rsaCheckV1(params,alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        if(signVerified){
            String app_id=params.get("app_id");
            if(!alipayConfig.getAppId().equals(app_id)){
                logger.error("异步验签app_id错误");
            }
            String out_trade_no=new String(request.getParameter("out_trade_no").
                    getBytes("ISO-8859-1"),"utf-8");
            String trade_no = new String(request.getParameter("trade_no").
                    getBytes("ISO-8859-1"),"UTF-8");
            String total_amount=new String(request.getParameter("total_amount").
                    getBytes("ISO-8859-1"),"UTF-8");
            ItemsShoppingRecord itemsShoppingRecord=getItemShoppingRecord(Long.parseLong(out_trade_no));
            if(!(itemsShoppingRecord!=null&&
                    itemsShoppingRecord.ifEquals(Long.parseLong(out_trade_no), Double.valueOf(total_amount))
            &&itemsShoppingRecord.getStatus()==ItemsShoppingRecord.Initial_State)){
                logger.error("订单信息不符或已成交！");
                return Result.fail("异步验签失败");
            }
            String tradeStatus=params.get("trade_status");
            if(tradeStatus.equals("TRADE_SUCCESS")||tradeStatus.equals("TRADE_FINISHED")){
                itemsShoppingRecord.setStatus(ItemsShoppingRecord.Paid_State);
                save(itemsShoppingRecord);
                logger.info("支付成功！已通过异步验签");
                return Result.success("支付成功！");
            }
        }else {
            logger.info("异步验签失败！");
            return Result.fail("异步验签失败");
        }
        logger.error("异步通知签名验证未通过");
        return Result.fail("异步通知签名验证未通过");
    }


    @Override
    public Result userCatchInstantItem(String itemName) throws Exception {
        RedisLock lock=new RedisLock(itemName,cacheClient.getRedisTemplate());
        LocalTime now=LocalTime.now();
        boolean res=false;
        while(LocalTime.now().getSecond()-now.getSecond()<3000000){
            res=lock.tryLock(1);
            if(res)break;
        }
        if(!res)return Result.fail("业务繁忙，请等候一下");
        Item item=itemsService.getInstantItem(itemName);
        if(item==null){
            return Result.fail("该物品不存在！");
        }
        if(item.getNum()<=0){
            return Result.fail("该物品已售罄！");
        }
        Account account= LoginUserInfo.getAccount();
        item.setNum(item.getNum()-1);
        cacheClient.setInstantItem(item);
        ItemsShoppingRecord itemsShoppingRecord=new ItemsShoppingRecord(item,1,account.getUserMail());
        itemsShoppingRecord.setStatus(ItemsShoppingRecord.Finish_State);
        CompletableFuture.runAsync(()->{
            save(itemsShoppingRecord);
        });
        return Result.success(itemsShoppingRecord);
    }








}
