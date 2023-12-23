package com.example.ooadgroupproject.service.Impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.example.ooadgroupproject.common.PayTool;
import com.example.ooadgroupproject.dao.ItemsShoppingRecordRepository;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class ItemsShoppingRecordServiceImpl implements ItemsShoppingRecordService {
    @Autowired
    private ItemsShoppingRecordRepository itemsShoppingRecordRepository;
    @Autowired
    private PayTool payTool;

    @Override
    public List<ItemsShoppingRecord> findAll() {
        return itemsShoppingRecordRepository.findAll();
    }

    @Override
    public List<ItemsShoppingRecord> findByUserMail(String userMail) {
        return itemsShoppingRecordRepository.findByUserMail(userMail);
    }

    @Override
    public List<ItemsShoppingRecord> findByItemName(String itemName) {
        return itemsShoppingRecordRepository.findByItemName(itemName);
    }
    //该方法用于请求支付宝完成金额交易，但是该方法不会完成减少库存的任务。减少库存的部分将在届时调用该方法的地方编写，以便于做高并发处理
    //暂时有些疑惑，我觉得可以加入查询状态功能，以应对更多场景
    //TODO
    //需要进一步优化！！
    @Override
    public boolean callAlipayToPurchase(String userMail, Item item, int num) throws AlipayApiException {
        //呼叫支付宝来结账
        ItemsShoppingRecord itemsShoppingRecord=new ItemsShoppingRecord(item,num,userMail);
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
        System.out.println(pageRedirectionData);
        //分两次存储，确保交易成功后，服务器即便突然崩溃，也能保证在用户支付成功前已将数据存储到数据库中
        if(response.isSuccess()){
            //接下来，确认已为用户请求跳转了支付宝页面，现在需要去确认用户是否已完成了支付

        }else {
            return false;
        }

        //返回true，表示成功
        return response.isSuccess();
    }

}
