package com.example.ooadgroupproject.service.Impl;

import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.example.ooadgroupproject.common.PayTool;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ItemsShoppingRecordRepository;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ItemsService;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

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
        return itemsShoppingRecordRepository.findAll();
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
    public ItemsShoppingRecord getItemShoppingRecord(long id){
        String value=cacheClient.getItemShoppingRecord(id);
        if(value!=null){
            if(value.isEmpty()){
                return null;
            }else {
                ItemsShoppingRecord itemsShoppingRecord= ItemsShoppingRecord.getItemShoppingRecordFromJson(value);
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
    public String queryBillData(Date date)throws AlipayApiException{
        //TODO
        AlipayClient alipayClient=payTool.getAlipayClient();
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        // model.setBillDate("2016-04-05");
//        AlipayDataDataserviceBillDownloadurlQueryModel model = payTool.getBillModel();
//        request.setBizModel(model);

        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        return "";
    }

    @Override
    public Result checkPayStatus(String itemShoppingRecordId){
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
        if(Objects.equals(itemsShoppingRecord.getStatus(), ItemsShoppingRecord.Initial_State)){
            try {
                String payInfo = queryAlipayStatus(recordId);
                logger.info(payInfo);
                if(payInfo.equals("TRADE_SUCCESS")||
                        payInfo.equals("TRADE_FINISHED")){
                    itemsShoppingRecord.setStatus(ItemsShoppingRecord.Purchased_State);
                    save(itemsShoppingRecord);
                    logger.info("支付成功！"+payInfo);
                    return Result.success(itemShoppingRecordId+"支付成功！");
                }else if(payInfo.equals("TRADE_CLOSED")){
                    logger.info(itemShoppingRecordId+"支付超时"+payInfo);
                    return Result.fail("支付超时！");
                }else if(payInfo.equals("WAIT_BUYER_PAY")||payInfo.equals("ACQ.TRADE_NOT_EXIST")){
                    logger.info(itemShoppingRecordId+"等待支付"+payInfo);
                    return Result.success("等待支付");
                }
            }catch (Exception e){
                logger.error(e.getMessage()+" "+itemShoppingRecordId+" ");
                return Result.fail("支付宝支付故障，请核对你的资金并与联系管理员解决！");
            }
        }else{
            return Result.fail("用户已购买该商品，无需此处再次查询！");
        }
        return Result.fail("未知原因导致失败，请联系管理员！");
    }

    @Override
    public Result alipayReturn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out=response.getWriter();
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
            if(itemsShoppingRecord!=null&&itemsShoppingRecord.ifEquals(Long.parseLong(out_trade_no), Double.valueOf(total_amount))){
                itemsShoppingRecord.setStatus(ItemsShoppingRecord.Purchased_State);
                //更新交易记录
                save(itemsShoppingRecord);
            }else {
                logger.error("订单信息不一致！");
                return Result.fail("订单信息不一致！");
            }
            logger.info("订单："+itemsShoppingRecord.getId()+"同步验证通过");
            return Result.success("trade_no:" + trade_no +
                    "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
        }else {
            logger.info("验签失败！");
            return Result.fail("验签失败");
        }
    }






}
