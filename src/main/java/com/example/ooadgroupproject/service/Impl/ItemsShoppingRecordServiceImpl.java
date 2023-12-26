package com.example.ooadgroupproject.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.example.ooadgroupproject.common.PayTool;
import com.example.ooadgroupproject.dao.ItemsShoppingRecordRepository;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.ItemsService;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ItemsShoppingRecord> findAll() {
        return itemsShoppingRecordRepository.findAll();
    }
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
        itemsShoppingRecordRepository.save(itemsShoppingRecord);
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
        itemsShoppingRecordRepository.save(itemsShoppingRecord);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
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
            return null;
        }
    }

    @Override
    public String queryBillData(Date date)throws AlipayApiException{
        //TODO
        AlipayClient alipayClient=payTool.getAlipayClient();
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        AlipayDataDataserviceBillDownloadurlQueryModel model = payTool.getBillModel(date.toString());

        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        return "";
    }






}
