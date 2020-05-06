package com.cloud.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.cloud.common.core.enums.ResultCodeEnum;
import com.cloud.config.AlipayConfig;
import com.cloud.exceptions.PaymentBizException;
import com.cloud.service.AlipayService;
import com.cloud.model.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/api/alipay")
public class AliPayController {

    private Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayClient alipayClient;

    @PostMapping("/index")
    public ModelAndView cashier(Order order) {
        ModelAndView mv = new ModelAndView();
        mv.addObject(order);
        mv.setViewName("cashier");
        return mv;
    }

    @GetMapping("/index")
    public ModelAndView cashier(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        String paymentAmount = request.getParameter("paymentAmount");
        ModelAndView mv = new ModelAndView();
        mv.addObject("orderId",orderId);
        mv.addObject("paymentAmount",paymentAmount);
        mv.setViewName("cashier");
        return mv;
    }

    @PostMapping(value = "pcPay")
    public String pcPay(HttpServletResponse response, Order order) {
//        try {
//            String form = alipayService.pcPay(order);
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write(form);
//            response.getWriter().flush();
//            response.getWriter().close();
//        } catch (Exception e) {
//            //
//        }
        return "/alipay/return";
    }



    @RequestMapping(value="pay",method= RequestMethod.POST)
    public void alipayNotify(HttpServletRequest request, HttpServletRequest response) {
        // 取出所有参数是为了验证签名

    }

    /**
     * 客户端提交订单支付请求，对该API的返回结果不用处理，浏览器将自动跳转至支付宝。
     * @param orderNo
     * @param subject
     * @param paymentAmount
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/pay",method = RequestMethod.POST)
    public void pay(
            @RequestParam String orderNo, @RequestParam String subject,
            @RequestParam String paymentAmount, HttpServletResponse response) throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNo(orderNo);
        orderDTO.setSubject(subject);
        orderDTO.setPaymentAmount(paymentAmount);
        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        try {
            String form = this.alipayService.pcPay(orderDTO);
            LOGGER.debug("[pay]" + "支付宝API生成的form表单内容：" + form);
            response.setContentType("text/html;charset="+ alipayConfig.getCharset());
            // 直接将完整的表单html输出到页面
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException e) {
            throw new PaymentBizException(ResultCodeEnum.PAYMENT_ALI_EXCEPTION, "alipay");
        }
    }

    // 接收支付宝返回的异步通知结果
    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    @ResponseBody
    public void notifyCallback(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new TreeMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String,String[]> entry : requestParams.entrySet()) {
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
        // 回调回来的参数要打日志，方便问题定位


        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV2(
                params,
                this.alipayConfig.getAliPublicKey(),
                this.alipayConfig.getFormat(),
                this.alipayConfig.getSignType());
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
            } else if ("TRADE_SUCCESS".equals(tradeStatus)){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }
            response.getWriter().write("success");
        } else {
            // 验签失败
            response.getWriter().write("fail");
        }

    }
    // 接收支付宝返回的同步通知结果
    @RequestMapping(value = "/return",method = RequestMethod.GET)
    public String returnCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = getRequestParams(request);
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV2(
                params,
                this.alipayConfig.getAliPublicKey(),
                this.alipayConfig.getFormat(),
                this.alipayConfig.getSignType());
        if(signVerified) {
            // 商户订单号
            String orderNo = request.getParameter("out_trade_no");
            // 支付宝交易号
            String tradeNo = request.getParameter("trade_no");
            // 交易状态
            String tradeStatus = request.getParameter("trade_status");
            // 判断这个订单是否已经支付过，根据订单状态判断，如果已经支付


            //
            return "redirect:/payment-success";
        } else {
            return "redirect:/payment-fail";
        }
    }

    private Map<String,String> getRequestParams(HttpServletRequest request) throws Exception {
        Map<String,String> params = new TreeMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String,String[]> entry : requestParams.entrySet()) {
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
