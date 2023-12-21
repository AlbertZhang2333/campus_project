package com.example.ooadgroupproject;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.example.ooadgroupproject.common.PayTool;


public class PayTest {
    public static void main(String[] args) throws Exception {

        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(PayTool.URL);
        alipayConfig.setAppId(PayTool.APPID);
        alipayConfig.setPrivateKey(PayTool.PRIVATE_KEY);
        alipayConfig.setFormat(PayTool.FORMAT);
        alipayConfig.setAlipayPublicKey(PayTool.ALIPAY_PUBLIC_KEY);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl("");
        //同步跳转地址，仅支持http/https
        request.setReturnUrl("");
        /******必传参数******/
        JSONObject bizContent = new JSONObject();
        //商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", "20210817010101004");
        //支付金额，最小值0.01元
        bizContent.put("total_amount", 0.01);
        //订单标题，不可使用特殊符号
        bizContent.put("subject", "测试商品");
        //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

        /******可选参数******/
        bizContent.put("time_expire", "2022-08-01 22:00:00");

        // 商品明细信息，按需传入
        JSONArray goodsDetail = new JSONArray();
        JSONObject goods1 = new JSONObject();
        goods1.put("goods_id", "goodsNo1");
        goods1.put("goods_name", "子商品1");
        goods1.put("quantity", 1);
        goods1.put("price", 0.01);
        goodsDetail.add(goods1);
        bizContent.put("goods_detail", goodsDetail);

        // 扩展信息，按需传入
        JSONObject extendParams = new JSONObject();
        extendParams.put("sys_service_provider_id", "2088511833207846");
        bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
