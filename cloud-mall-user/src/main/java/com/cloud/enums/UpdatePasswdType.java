package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum UpdatePasswdType {

    SMS(1,"短信"),
    EMAIL(2,"邮件");

    private int code;

    private String desc;

    UpdatePasswdType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, UpdatePasswdType> codeMap;
    private static Map<String, UpdatePasswdType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (UpdatePasswdType element : UpdatePasswdType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static UpdatePasswdType getCouponState(int code) {

        return codeMap.get(code);
    }

    public static UpdatePasswdType getCouponState(String desc) {

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
