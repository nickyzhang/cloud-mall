package com.cloud.web.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:wxpay.properties")
@Configuration
public class WeiXinPayConfig {

    @Value("${wxpay.app_id}")
    private String appId;

    @Value("${wxpay.app_secret}")
    private String appSecret;

    @Value("${wxpay.mch_id}")
    private String mchId;

    @Value("${wxpay.sign_key}")
    private String key;

    @Value("${wxpay.callback_url}")
    private String callbackUrl;

    @Value("${wxpay.notify_url}")
    private String notifyUrl;

    @Value("${wxpay.order_query_url}")
    private String orderQueryUrl;

    @Value("${wxpay.download_bill_url}")
    private String downloadBillUrl;

    @Value("${wxpay.pay_rate}")
    private float payRate;

    @Value("${wxpay.refund_url}")
    private String refundUrl;

    @Value("${wxpay.refund_query_url}")
    private String refundQueryUrl;

    @Value("${wxpay.trade_type}")
    private String tradeType;

    @Value("${wxpay.unified_order_url}")
    private String unifiedOrderUrl;

    @Value("${wxpay.cert}")
    private String cert;

}
