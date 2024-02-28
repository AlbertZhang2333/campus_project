package com.example.ooadgroupproject.common;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayDataDataserviceBillDownloadurlQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PayTool {
    public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    public static String APPID = "9021000133619219";
    public static String PRIVATE_KEY="" ;
    public static String FORMAT = "json";

//    public static String RETURN_URL="http://mhgnq3.natappfree.cc/UserShopping/AliPayReturn";
//    public static String NOTIFY_URL="http://mhgnq3.natappfree.cc/UserShopping/AlipayNotify";
    public static String RETURN_URL="http://f91556a061bd9a24.natapp.cc/UserShopping/AliPayReturn";
    public static String NOTIFY_URL="http://f91556a061bd9a24.natapp.cc/UserShopping/AlipayNotify";
    public static String CHARSET = "utf-8";
    public static String SIGN_TYPE = "RSA2";
    public static String ALIPAY_PUBLIC_KEY ="";
    public AlipayConfig getAliPayConfig(){
        AlipayConfig alipayConfig=new AlipayConfig();
        alipayConfig.setServerUrl(URL);
        alipayConfig.setAppId(APPID);
        alipayConfig.setPrivateKey(PRIVATE_KEY);
        alipayConfig.setFormat(FORMAT);
        alipayConfig.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        alipayConfig.setCharset(CHARSET);
        alipayConfig.setSignType(SIGN_TYPE);
        return alipayConfig;
    }
    public AlipayClient getAlipayClient() throws AlipayApiException {
        return new DefaultAlipayClient(getAliPayConfig());
    }
    public AlipayTradePagePayModel getPurchaseModel(ItemsShoppingRecord itemsShoppingRecord){
        AlipayTradePagePayModel model=new AlipayTradePagePayModel();
        model.setOutTradeNo(itemsShoppingRecord.getId()+"");
        model.setTotalAmount(itemsShoppingRecord.getAmount()+"");
        model.setSubject(itemsShoppingRecord.getItemName());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        return model;
    }

    public AlipayTradeQueryModel getQueryModel(String tradeNo){
        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
        model.setOutTradeNo(tradeNo);
        return model;
    }
    public AlipayDataDataserviceBillDownloadurlQueryModel getBillModel(String billData){
        AlipayDataDataserviceBillDownloadurlQueryModel model=new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillType("trade");
        model.setBillDate(billData);
        return model;
    }
}

