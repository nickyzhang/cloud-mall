package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum CouponStatus {

    UNUSED(1, "未使用"),
    USED(2,"已使用"),
    EXPIRED(3,"已过期");

    private int code;

    private String desc;

    CouponStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, CouponStatus> codeMap;
    private static Map<String, CouponStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (CouponStatus element : CouponStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static CouponStatus getCouponStatus(int code) {

        return codeMap.get(code);
    }

    public static CouponStatus getCouponStatus(String desc) {

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
