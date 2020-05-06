package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {

    ENABLED(1,"启用"),
    DISABLED(2,"禁用");

    private int code;

    private String desc;

    UserStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, UserStatus> codeMap;
    private static Map<String, UserStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (UserStatus element : UserStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static UserStatus getCouponState(int code) {

        return codeMap.get(code);
    }

    public static UserStatus getCouponState(String desc) {

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
