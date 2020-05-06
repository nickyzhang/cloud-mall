package com.cloud.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cloud.web.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/pay/alipay")
public class AlipayController {

    @Autowired
    private AlipayConfig aliPayConfig;

    @RequestMapping("/web-pc")
    public void alipay(@RequestParam("outTradeNo") Long outTradeNo,
                       @RequestParam("amount")BigDecimal amount,
                       @RequestParam("subject") String subject,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception{
        AlipayClient alipayClient = new DefaultAlipayClient(this.aliPayConfig.getUrl(),
                this.aliPayConfig.getAppId(),
                this.aliPayConfig.getFormat(),
                this.aliPayConfig.getAliPublicKey(),
                this.aliPayConfig.getCharset(),
                this.aliPayConfig.getSignType());

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(this.aliPayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(this.aliPayConfig.getNotifyUrl());

        Map<String,String> params = new HashMap<>();
        // 商户订单号，即我方的订单号
        params.put("out_trade_no",String.valueOf(outTradeNo));
        // 付款金额
        params.put("total_amount",String.valueOf(amount));
        // 订单名称
        params.put("subject",subject);

        String bizContent = JSONObject.toJSONString(params);
        alipayRequest.setBizContent(bizContent);

        // form表单
        String form = alipayClient.pageExecute(alipayRequest).getBody();
        response.setContentType("text/html;charset=" + aliPayConfig.getCharset());
        // 直接将完整的表单html输出到页面
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping(value="/return", method = RequestMethod.GET)
    public void payCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取支付宝GET过来反馈信息
//        Map<String, String> params = getAlipayRequestParams(request);
//
//        boolean signVerified = AlipaySignature.rsaCheckV2(params,
//                this.aliPayConfig.getPublicKey(),
//                this.aliPayConfig.getCharset(),
//                aliPayConfig.getSignType()); //调用SDK验证签名
//
//        // 验签
//        if (signVerified) {
//            //商户订单号
//            String outTradeNo = new String(params.get("out_trade_no"));
//            //支付宝交易号
//            String tradeNo = new String(params.get("trade_no"));
//            //交易状态
//            String tradeStatus = new String(params.get("trade_status"));

            // 进行自身业务的实现
//            1.插入交易记录到数据库
//            2.修改订单状态和排期座位的状态
//            if (!dmTradeService.processed(out_trade_no, Constants.PayMethod.ZHIFUBAO)) {
//                dmTradeService.insertTrade(out_trade_no, trade_no, Constants.PayMethod.ZHIFUBAO);
//            }
//
//            System.out.println("进行自身业务处理");
//            response.getWriter().write("trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
//            response.sendRedirect(String.format(aliPayConfig.getPaymentSuccessUrl(), out_trade_no));
//        } else {
//            response.getWriter().write("验签失败");
//            response.sendRedirect(aliPayConfig.getPaymentFailUrl());
//        }
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
