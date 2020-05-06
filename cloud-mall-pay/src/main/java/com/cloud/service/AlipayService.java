package com.cloud.service;

import com.alipay.api.AlipayApiException;
import com.cloud.dto.pay.OrderDTO;
import com.cloud.model.order.Order;
import java.util.Map;

public interface AlipayService {

    /**
     * 阿里支付预下单
     * 如果你调用的是当面付预下单接口(alipay.trade.precreate)，调用成功后订单实际上是没有生成，因为创建一笔订单要买家、卖家、金额三要素。
     * 预下单并没有创建订单，所以根据商户订单号操作订单，比如查询或者关闭，会报错订单不存在。
     * 当用户扫码后订单才会创建，用户扫码之前二维码有效期2小时，扫码之后有效期根据timeout_express时间指定。
     * 2018年起，扫码支付申请需要门店拍照等等，申请流程复杂了
     * @param order
     * @return
     */
    public String preparePay(Order order);

    /**
     * 阿里支付退款
     * @param order
     * @return
     */
    public String refund(Order order);


    /**
     * 关闭订单
     * @param order
     * @return
     */
    public String closeOrder(Order order);

    /**
     * 下载对账单
     * @param billDate (账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM)
     * @param billType (trade、signcustomer；trade指商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单；)
     * @return
     */
    public String downloadBill(String billDate, String billType);

    /**
     * PC支付
     * @param order
     * @return
     */
    public String pcPay(OrderDTO order) throws AlipayApiException;

    /**
     * 手机支付,比如H5
     * @param order
     * @return
     */
    public String mobilePay(Order order);

    /**
     * app支付
     * @param order
     * @return
     */
    public String appPay(Order order);

    /**
     * 验证签名
     * @param map
     * @return
     */
    public boolean verify(Map<String, String> map);
}
