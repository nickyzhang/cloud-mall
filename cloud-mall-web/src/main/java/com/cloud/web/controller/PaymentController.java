package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.constants.SymbolConstants;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.common.core.utils.ServletUtils;
import com.cloud.common.service.RestService;
import com.cloud.config.AlipayConfig;
import com.cloud.config.WeiXinPayConfig;
import com.cloud.enums.PaymentType;
import com.cloud.enums.SignType;
import com.cloud.model.order.OrderItem;
import com.cloud.po.pay.GoodsParam;
import com.cloud.po.pay.PayParam;
import com.cloud.utils.WXPayUtil;
import com.cloud.vo.order.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@Slf4j
public class PaymentController {

    @Autowired
    RestService restService;

    @Autowired
    AlipayConfig alipayConfig;

    @Autowired
    WeiXinPayConfig wxpayConfig;

    @PostMapping("/cashier")
    public ModelAndView cashier(@RequestParam("orderId") Long orderId) {
        ModelAndView mv = new ModelAndView();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        mv.addObject("orderId", orderId);
        mv.setViewName("cashier");
        return mv;
    }

    @PostMapping("/cashier/web/pc_pay")
    public ModelAndView payPC(@RequestParam("orderId") Long orderId, @RequestParam("payCode") String payCode, @RequestParam("payType") int payType) {
        if (payType == PaymentType.ALIPAY_PC.getCode()) {
            return aliPCPay(orderId);
        } else if (payType == 3) {
            return wxpay(orderId);
        }
    }


    private ModelAndView aliPCPay(Long orderId) {
        ModelAndView mv = new ModelAndView();
        OrderVO orderVO = getOrder(orderId);
        PayParam payParam = new PayParam();
        payParam.setPayType(1);
        payParam.setOrderId(orderVO.getOrderId());
        payParam.setOrderNo(orderVO.getOrderNo());
        payParam.setOrderType(orderVO.getOrderType());
        payParam.setOrderDateTime(orderVO.getCreateTime());
        payParam.setTradeAmount(orderVO.getPaymentAmount());
        payParam.setOutTradeNo(orderVO.getOrderNo());
        payParam.setSubject("云购-订单编号: " + orderVO.getOrderNo());
        payParam.setCurrency("CNY");
        payParam.setExpiredMinutes(1440);
        payParam.setReturnUrl(this.alipayConfig.getReturnUrl());
        payParam.setNotifyUrl(this.alipayConfig.getNotifyUrl());
        List<OrderItem> itemList = orderVO.getItemList();
        List<GoodsParam> goods = new ArrayList<>();
        for (OrderItem item : itemList) {
            GoodsParam good = new GoodsParam();
            good.setGoodsId(item.getSkuId());
            good.setGoodsName(item.getSkuName());
            good.setPrice(item.getUnitPrice());
            good.setQuantity(item.getQuantity());
            goods.add(good);
        }
        payParam.setItems(goods);
        String sign = null;
        try {
            sign = AlipaySignature.rsa256Sign("", this.alipayConfig.getPrivateKey(), this.alipayConfig.getCharset());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        payParam.setSign(sign);
        payParam.setSignType(this.alipayConfig.getSignType());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        JSONObject payJSON = this.restService.postForObject("http://api.cloud.com/pay/doPay", JSONObject.class, params);
        TypeReference<ResponseResult> payResultType = new TypeReference<ResponseResult>() {
        };
        ResponseResult payResult = payJSON.toJavaObject(payResultType);
        if (payResult.getCode() != 200) {
            mv.addObject("errMsg", payResult.getMsg());
            mv.setViewName("redirect:/cashier/web/pc_pay");
        }
        return mv;
    }

    /**
     * 对于支付宝甚至重要数据时，防止别人篡改，所以我们需要在请求添加签名，自己本地保留私钥，服务器保留公钥
     * 服务器根据公钥来验签。
     * 支付宝有种集成方式：
     * #1 是基于老版本的，未使用开放平台的
     * #2 一种是基于开放平台的SDK
     *
     * @return
     */
    private String sign(OrderVO orderVO) {
        String content = JSONObject.toJSONString(orderVO);
        String sign = null;
        try {
            if (this.alipayConfig.getSignType().equals("RSA2")) {
                sign = AlipaySignature.rsa256Sign(content, this.alipayConfig.getPrivateKey(),
                        this.alipayConfig.getCharset());
            } else {
                sign = AlipaySignature.rsaSign(content, this.alipayConfig.getPrivateKey(),
                        this.alipayConfig.getCharset());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return sign;
    }

    // 微信支付需要扫码，所以先得产生一个二维码，展示给用户，当用户选择微信支付的时候，就该返回这个二维码的URL
    public ResponseResult createQRCode(@RequestParam("orderId") Long orderId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        OrderVO orderVO = getOrder(orderId);
        // 封装请求下单
        Map<String, String> data = new HashMap<>();
        data.put("appid", this.wxpayConfig.getAppId());
        data.put("mch_id", this.wxpayConfig.getMchId());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", "");
        data.put("out_trade_no", String.valueOf(orderVO.getOrderNo()));
        data.put("total_fee", String.valueOf(orderVO.getPaymentAmount()));
        data.put("spbill_create_ip", ServletUtils.getIpAddress(request));
        data.put("notify_url", this.wxpayConfig.getNotifyUrl());
        data.put("trade_type", "NATIVE");

        // 签名
        String sign = null;
        try {
            sign = WXPayUtil.generateSignedXml(data, this.wxpayConfig.getKey(), SignType.MD5);
        } catch (Exception e) {
            // 抛出支付异常
            e.printStackTrace();
        }
        data.put("sign", sign);

        // 支付,统一下单
        StringBuilder sb = new StringBuilder(this.wxpayConfig.getUnifiedOrderUrl());
        if (MapUtils.isNotEmpty(data)) {
            sb.append(SymbolConstants.QUESQION_SYMBOL);
            data.forEach((k, v) -> {
                sb.append(k).append(SymbolConstants.AND_SYMBOL).append(v);
            });
        }
        Map<String, Object> results = this.restService.postForObject(sb.toString(), JSONObject.class);
        // 拿到返回结果中的code_url
        String resultCode = (String) results.get("result_code");
        String codeUrl = null;
        map.put("tradeNo", orderVO.getOrderNo());
        map.put("payCode", PaymentType.WXPAY.getDesc());
        map.put("payType", PaymentType.WXPAY.getCode());

        if ("SUCCESS".equals(resultCode)) {
            codeUrl = (String) results.get("code_url");
            map.put("submitUrl", codeUrl);
            return new ResponseResult().success("成功", map);
        } else {
            return new ResponseResult().failed("支付失败,无法产生二维码");
        }
    }


    private OrderVO getOrder(Long orderId) {
        JSONObject getOrderJSON = restService.getForObject("http://api.cloud.com/order/findOrderById", JSONObject.class, params);
        TypeReference<ResponseResult<OrderVO>> getOrderResultType = new TypeReference<ResponseResult<OrderVO>>() {
        };
        ResponseResult<OrderVO> getOrderResult = getOrderJSON.toJavaObject(getOrderResultType);
        return getOrderResult.getData();
    }


    // 接受支付结果通知的接口
    @PostMapping(value = "/pay/wxpay/notify")
    @ResponseBody
    public String wxpayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        Map<String, String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        callbackMap = WXPayUtil.getSortedMap(callbackMap);
        Map<String, String> returnMap = new HashMap<>();
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
                returnMap.put("return_code", "SUCCESS");
                returnMap.put("return_msg", "OK");

            } else {
                // 如果没有成功，则返回一个
                returnMap.put("return_code", "FAIL");
                returnMap.put("return_msg", "已支付");
            }
        } else { // 签名验签失败
            returnMap.put("return_code", "FAIL");
            returnMap.put("return_msg", "验签失败");
        }

        // 返回我们的接受状态
        return WXPayUtil.mapToXml(returnMap);
    }

    @PostMapping(value = "/pay/alipay/notify")
    @ResponseBody
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = getAlipayRequestParams(request);
        // 回调回来的参数要打日志，方便问题定位
        // 1.日志记录
        log.info("####支付宝同步通知synCallBack开始， params={}#####", params);

        //调用SDK验证签名,验证支付宝的签名
        boolean signVerified = AlipaySignature.rsaCheckV2(
                params,
                this.alipayConfig.getAliPublicKey(),
                this.alipayConfig.getFormat(),
                this.alipayConfig.getSignType());
        log.info("####支付宝异步通知，signVerified={}", signVerified);
        // 如果业务方响应的是success，支付宝才会认为是业务方完成支付，不会重复发送消息，否则会重复发送消息
        if (signVerified) {
            // 商户订单号
            String orderNo = request.getParameter("out_trade_no");
            // 支付宝交易号
            String tradeNo = request.getParameter("trade_no");
            // 交易状态
            String tradeStatus = request.getParameter("trade_status");
            // 进行自身业务的实现
            // 插入交易记录
            // 修改订单状态
            if ("TRADE_FINISHED".equals(tradeStatus)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
            } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
            response.getWriter().write("success");
        } else {
            log.error("#####支付宝同步通知验签失败####");
            // 验签失败
            response.getWriter().write("fail");
        }
    }

    @RequestMapping("/pay/alipay/return")
    public String alipayReturnCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = getAlipayRequestParams(request);
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV2(
                params,
                this.alipayConfig.getAliPublicKey(),
                this.alipayConfig.getFormat(),
                this.alipayConfig.getSignType());
        if (signVerified) {
            // 商户订单号
            String orderNo = request.getParameter("out_trade_no");
            // 支付宝交易号
            String tradeNo = request.getParameter("trade_no");
            // 交易状态
            String tradeStatus = request.getParameter("trade_status");
            // 判断这个订单是否已经支付过，根据订单状态判断，如果已经支付

            return "redirect:/pay/success";
        } else {
            return "redirect:/pay/fail";
        }
    }

    /**
     * 获取支付宝返回的请求参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    private Map<String, String> getAlipayRequestParams(HttpServletRequest request) throws Exception {
        Map<String, String> params = new TreeMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            String value = "";
            for (int i = 0; i < values.length; i++) {
                value = (i == values.length - 1) ? value + values[i] : value + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            value = new String(value.getBytes("UTF-8"), "UTF-8");
            params.put(name, value);
        }
        return params;
    }
}
