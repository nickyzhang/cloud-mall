package com.cloud.vo.pay;

import lombok.Data;

@Data
public class AlipayVO {
    //订单号
    private String putTradeNo;
    //订单名称
    private String subject;
    //付款金额
    private double totalAmount;
    //销售产品码
    private String productCode;
}

