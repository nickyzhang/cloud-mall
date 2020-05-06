package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum ReturnMethod {

    PICKUP(1,"上门取件"),
    POST(2,"自寄");

    private int code;

    private String desc;

    ReturnMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ReturnMethod> codeMap;
    private static Map<String, ReturnMethod> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (ReturnMethod element : ReturnMethod.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static ReturnMethod getReturnMethod(int code) {

        return codeMap.get(code);
    }

    public static ReturnMethod getReturnMethod(String desc) {

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
