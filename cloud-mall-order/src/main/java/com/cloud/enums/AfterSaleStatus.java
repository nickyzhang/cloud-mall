package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum AfterSaleStatus {

    WAITING_AUDIT(1,"待审核"),
    WAITING_PICKUP(2,"待上门取件"),
    WAITING_POST(3,"待邮寄"),
    WAITING_RETURN(4,"待退货"),
    WAITING_EXCHANGE(5,"待换货"),
    REFUNDING(6,"退款中"),
    CLOSED(7,"已完成");

    private int code;

    private String desc;

    AfterSaleStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, AfterSaleStatus> codeMap;
    private static Map<String, AfterSaleStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (AfterSaleStatus element : AfterSaleStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static AfterSaleStatus getAfterSaleStatus(int code) {

        return codeMap.get(code);
    }

    public static AfterSaleStatus getAfterSaleStatus(String desc) {

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
