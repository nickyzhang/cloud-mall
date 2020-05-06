package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum RefundStatus {


    REFUNDING(1,"退款中"),
    COMPLETED(2,"已退款");

    private int code;

    private String desc;

    RefundStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, RefundStatus> codeMap;
    private static Map<String, RefundStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (RefundStatus element : RefundStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static RefundStatus getRefundStatus(int code) {

        return codeMap.get(code);
    }

    public static RefundStatus getRefundStatus(String desc) {

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
