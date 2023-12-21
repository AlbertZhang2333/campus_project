package com.example.ooadgroupproject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.example.ooadgroupproject.common.PayTool;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.domain.AlipayTradeRefundModel;
public class refundTest {
    public static void main(String[] args) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(PayTool.URL);
        alipayConfig.setAppId(PayTool.APPID);
        alipayConfig.setPrivateKey(PayTool.PRIVATE_KEY);
        alipayConfig.setFormat(PayTool.FORMAT);
        alipayConfig.setAlipayPublicKey(PayTool.ALIPAY_PUBLIC_KEY);
        alipayConfig.setCharset(PayTool.CHARSET);
        alipayConfig.setSignType(PayTool.SIGN_TYPE);
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setRefundAmount("200.12");
        //补充model内的内容，如订单号
        request.setBizModel(model);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            // String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            // System.out.println(diagnosisUrl);
        }
    }
}
