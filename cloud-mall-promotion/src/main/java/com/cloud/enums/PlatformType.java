package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum PlatformType {

    PLATFORM_COUPON(1, "平台券"),
    SHOP_COUPON(2,"店铺券");

    private int code;

    private String desc;

    PlatformType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PlatformType> codeMap;
    private static Map<String, PlatformType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (PlatformType element : PlatformType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static PlatformType getPlatformType(int code) {

        return codeMap.get(code);
    }

    public static PlatformType getPlatformType(String desc) {

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
