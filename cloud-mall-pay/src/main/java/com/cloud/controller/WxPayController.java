package com.cloud.controller;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.ServletUtils;
import com.cloud.common.httpclient.service.HttpClientService;
import com.cloud.config.WeiXinPayConfig;
import com.cloud.enums.SignType;
import com.cloud.model.order.Order;
import com.cloud.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/wxpay")
public class WxPayController {

    @Autowired
    private WeiXinPayConfig wxpayConfig;

    @Autowired
    private HttpClientService httpClient;

    /**
     * 请求统一下单
     * @param request
     * @param order
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/createqrcode/{orderNo}")
    @ResponseBody
    public ResponseResult createQRCode(HttpServletRequest request, Order order) throws Exception {
        // 获取订单信息

        // 封装请求下单
        Map<String,String> data = new HashMap<>();
        data.put("appid",this.wxpayConfig.getAppId());
        data.put("mch_id", this.wxpayConfig.getMchId());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body","");
        data.put("out_trade_no",String.valueOf(order.getOrderNo()));
        data.put("total_fee",String.valueOf(order.getPaymentAmount()));
        data.put("spbill_create_ip", ServletUtils.getIpAddress(request));
        data.put("notify_url",this.wxpayConfig.getNotifyUrl());
        data.put("trade_type","NATIVE");

        // 签名
        String sign = WXPayUtil.generateSignedXml(data, this.wxpayConfig.getKey(), SignType.MD5);
        data.put("sign",sign);

        // 支付,统一下单
        Map<String,Object> results = this.httpClient.doPost(this.wxpayConfig.getUnifiedOrderUrl(),data);
        // 拿到返回结果中的code_url
        String resultCode = (String)results.get("result_code");
        String codeUrl = null;
        if ("SUCCESS".equals(resultCode)) {
            codeUrl = (String)results.get("code_url");
            return new ResponseResult().success(codeUrl);
        } else {
            throw new PaymentBizException(ResultCodeEnum.PAYMENT_WX_EXCEPTION);
        }
    }

    // 接受支付结果通知的接口
    @PostMapping(value = "/notify")
    @ResponseBody
    public String paymentCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 接受微信官方返回的支付结果
        InputStream inputStream = request.getInputStream();
        //BufferedReader是包装设计模式，性能更好
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        inputStream.close();
        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        callbackMap = WXPayUtil.getSortedMap(callbackMap);
        Map<String,String> returnMap = new HashMap<>();
        // 签名校验
        if (WXPayUtil.isResponseSignatureValid(callbackMap, this.wxpayConfig.getKey(), SignType.HMACSHA256)) {
            // 获取支付结果的result_code，根据这个值判断是否进行自身业务实现
            String resultCode = callbackMap.get("result_code");
            if ("SUCCESS".equals(resultCode)) {
                // 业务方实现自己业务，比如更改订单状态，添加积分，支付记录等
                // 获取微信返回的交易订单号
                String transactionId = callbackMap.get("transaction_id");
                // 获取商户订单号
                String outTradeNo = callbackMap.get("out_trade_no");
                // 判断这个订单是否支付过，如果没支付，则进行没有支付的业务处理
                // 1 往往交易表插入支付记录
                // 2 发消息修改订单状态
                returnMap.put("return_code","SUCCESS");
                returnMap.put("return_msg","OK");

            } else {
                // 如果没有成功，则返回一个
                returnMap.put("return_code","FAIL");
                returnMap.put("return_msg","已支付");
            }
        } else { // 签名验签失败
            returnMap.put("return_code","FAIL");
            returnMap.put("return_msg","验签失败");
        }

        // 返回我们的接受状态
        return WXPayUtil.mapToXml(returnMap);
    }

}
