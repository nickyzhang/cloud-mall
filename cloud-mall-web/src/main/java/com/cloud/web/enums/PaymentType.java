package com.cloud.web.enums;

import java.util.HashMap;
import java.util.Map;

public enum PaymentType {

    ALIPAY_PC(1,"ALIPAY_PC"),
    ALIPAY_APP(2,"ALIPAY_APP"),
    WXPAY(3,"WXPAY");

    private int code;

    private String desc;

    PaymentType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PaymentType> codeMap;
    private static Map<String, PaymentType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (PaymentType element : PaymentType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static PaymentType getPaymentType(int code) {

        return codeMap.get(code);
    }

    public static PaymentType getPaymentType(String desc) {

        return descMap.get(desc);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
