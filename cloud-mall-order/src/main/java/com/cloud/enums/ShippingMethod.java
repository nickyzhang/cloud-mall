package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum ShippingMethod {


    EXPRESS(1,"快递"),
    PRIORITY(2,"优先"),
    STANDARD(3,"标准");

    private int code;

    private String desc;

    ShippingMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ShippingMethod> codeMap;
    private static Map<String, ShippingMethod> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (ShippingMethod element : ShippingMethod.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static ShippingMethod getShippingMethod(int code) {

        return codeMap.get(code);
    }

    public static ShippingMethod getShippingMethod(String desc) {

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
