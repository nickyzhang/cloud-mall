package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderType {

    SECKILL(1,"秒杀"),
    STANDARD(2,"标准");

    private int code;

    private String desc;

    OrderType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderType> codeMap;
    private static Map<String, OrderType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (OrderType element : OrderType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static OrderType getOrderType(int code) {

        return codeMap.get(code);
    }

    public static OrderType getOrderType(String desc) {

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
