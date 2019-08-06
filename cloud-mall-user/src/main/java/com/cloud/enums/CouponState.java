package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum CouponState {

    UNUSED(1,"未使用"),
    USING(2,"使用中"),
    USED(3,"已使用");

    private int code;

    private String desc;

    CouponState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, CouponState> codeMap;
    private static Map<String, CouponState> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (CouponState element : CouponState.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static CouponState getCouponState(int code) {

        return codeMap.get(code);
    }

    public static CouponState getCouponState(String desc) {

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
