package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum StockType {

    TOTAL_STOCK(1,"total"),
    AVAILABLE_STOCK(2,"available"),
    FROZEN_STOCK(3,"frozen");

    private int code;

    private String desc;

    StockType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, StockType> codeMap;
    private static Map<String, StockType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (StockType element : StockType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static StockType getCouponState(int code) {

        return codeMap.get(code);
    }

    public static StockType getCouponState(String desc) {

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
