package com.cloud.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.config.AlipayConfig;
import com.cloud.dto.pay.OrderDTO;
import com.cloud.model.order.Order;
import com.cloud.service.AlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayServiceImpl.class);

    public static final String QRCODE_PATH = "";

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public String preparePay(Order order) {
        LOGGER.info("订单号：{}生成支付宝支付码",order.getOrderNo());

        return null;
    }

    @Override
    public String refund(Order order) {
        return null;
    }

    @Override
    public String closeOrder(Order order) {
        return null;
    }

    @Override
    public String downloadBill(String billDate, String billType) {
        return null;
    }

    @Override
    public String pcPay(OrderDTO order) throws AlipayApiException {
        LOGGER.info("支付宝PC支付下单,订单号: {}", order.getOrderNo());
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(order.getOrderNo());
        model.setSubject(order.getSubject());
        model.setTotalAmount(String.valueOf(order.getPaymentAmount()));
        model.setBody("支付测试，共0.01元");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(this.alipayConfig.getReturnUrl());
        request.setNotifyUrl(this.alipayConfig.getNotifyUrl());
        request.setBizModel(model);
        LOGGER.info("业务参数:"+ JSONUtils.objectToJson(request.getBizModel()));

        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        AlipayTradePagePayResponse response = this.alipayClient.pageExecute(request);
        return response.getBody();
    }


    @Override
    public String mobilePay(Order order) {
        return null;
    }

    @Override
    public String appPay(Order order) {
        return null;
    }

    @Override
    public boolean verify(Map<String, String> map) {
        return false;
    }
}
