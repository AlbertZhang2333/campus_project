package com.example.ooadgroupproject.common;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.example.ooadgroupproject.entity.ItemsShoppingRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PayTool {
    public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    public static String APPID = "9021000133619219";
    public static String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqgtfUyS9ob2UI1c8tZunrG5pq1uJ/EvTpIejLNR6o55IHt2WmUMAIyleTVyV0ZAveSbg4IIlZeMkBuCs/FR9Nt0aasTRZRjyCoDneN5bxXE08bMnsMBD7jAcP9NaNS8TQKEbiKHbWU7Af5V+9qZBY1VpHBsVU4v1IX1SQyspklxQh+FxVGNaLMiYaRFhqW7G0RJIxQEEthQythqWA/NfVY3I55HmB3J1dRrweVgBLBS+28Kt2ce26i7T68oOcFVp8jV2Lr2ZWCP+kTUIf/9kR7Bo7qNyoh/b097kp1qdnSLnYH9LAe/093sKptcX9tiLjGwBcOW4bBPx4b1s5ldxvAgMBAAECggEBAJJ0jjPblhfKzmpXU3s40SS68dhgt+zT+H6iPJUGDE6hkKGGGg5Pf5JNUglhKe+d2z/T7CJxd+fcwjHy4VpupxOWn1NRb5haprDANNQ0xTaMxF5pRsrSwlN9g7dl4j883FC6t5+bh+8xtK7A0opSsYRLxR4Kp8XwrgLStw0i0dKxNpMzxt0gA972rMZa/9KzUcEZR6AewagEd4zN3CNB5FvD+JH/a7YFWLdD4Ua8mHfUJ0YNpVrp83Lp4VtUkVbxCmk+Id6xIJACXBeLrrGofNzXpT22gNoUKp2qqPmLRqfo5zZ28TXPV1cQrH4x/qTDCLwZ8UI9g0c27sz4BLf3CvkCgYEA0n/+b9MArRO0UsKnA8nIVYNkDyoeZRiyzwUGiwALnzjOs+nYDem8ADrPMwlO+VWV7sP/YQ5KiTABTheAMbSSRb6SjFzvmryt41cn0a5fkSXCJvU1+dJZ3B6bbYiPLm5OVGj1ACcSDGtx5AW0lYM98tXQ74czOsRlgJcYRoWtNhMCgYEAz14RJQUA1PEHZJ3t2A0DO90AeG8pT1wiesZZq50+OeZ1XwXZ7EIsfULKOuqWhwSoZEn6h6iYQh/C7Nxs0+RFs832eTTPuZ75CEX2NZNcXvJOPce1GEnuNtfrTpudtPrakyIlJNjoXBNKWXDadgiTcL0458BE5OPfPUjpmUC++7UCgYBGffx8AEJas52QlYUOvLUtBeotNeKl4maG81zkkahSstht4aKrzhlaTldy9OwQjXu/UL2mB/4oAP5+41nXy2uslYxIGxtWS1dEKby4+FciqW1iVH214p6bG1jbjn68gs4ScG+TlIHzzMuAG/UEmMXXG4lznWgnhvui23aSr1N5JwKBgDAJrJMU26J4y6ulYkngidU+VzOPHNTDlUE33eIBpT6ogpwmsNsoYUILTOnlGkXg8Fl7QcxXZB49WVyhm5xijzseBXECqPRpPWuLOYQjxQxBuZpaPzuHNOYDkwE8z4XzbtsS1+P3U6yw01Jw8ErTpcRCMYKOaedUWozJ7UORWizZAoGAdGs06VxlYs5AGdm89f/8S7AsjMA0EGsKP+xDVeyIY52ij3qcb3096R/4fd4twZy2U/395mLR12wv7JQEGrEHCr9vfc6KPkq00wyscuY8BSQ1RwxxxmHMLJN5szLI98nI+hBiBinYGzJN9tNfiwAXBePP5sSnR97XSUHhGwt9zhg=";
    public static String FORMAT = "json";
    public static String CHARSET = "utf-8";
    public static String SIGN_TYPE = "RSA2";
    public static String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoVdpo0TZWsLEBGh5aJQuh1eYi/6sxTcV9KEnXMq3N6TZpaM8acFhMrrx42VAhwbxICbk93vNOSkoBp30twovzUFe87oT2dw+kti/Kr+QN3mX7KXBLyfW3+6zf+dnzzWG+D9Q+5UdFh/JGpN2TWfR8UMgy/NGfwnS5QIEtKDPjCvrA90CXLLjBbuZpzk8saj8DwXpX8hSi5G5QBTqIRl924z/ITcTfggRTC2Eh4F6/8Khy3oC6XDP0HIxyZ7CvGfLgAVdlXZgrTTTre2/iuZFBdDZtTutrRsyviCxsHjDgo3IYP1DzZpZ8HND70k0S+wz47veDYahh05Cg0rKG06AfwIDAQAB";

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
}

