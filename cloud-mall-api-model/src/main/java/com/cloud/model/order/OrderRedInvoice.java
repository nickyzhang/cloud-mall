package com.cloud.model.order;

public class OrderRedInvoice {

    /** 纳税人识别号 String(15-20) 是 开票方纳税人识别号 */
    private String taxpayerNum;

    /** 发票号码 String(8) 是 需冲红原发票号码 */
    private String invoiceNo;

    /** 发票代码 String(12) 是 需冲红原发票代码 */
    private String code;

    /** 冲红原因 String(1-100)*/
    private String reason;

    /** 价税合计金额 10 位(精确到 2位小数)是 原发票的价税合计金额,的负数值 */
    private String amount;
}
