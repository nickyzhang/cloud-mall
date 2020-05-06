package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum CouponType {

    CASH_COUPON(1,"现金券"),
    MONEY_OFF_COUPON(2,"满减券"),
    DISCOUNT_OFF_COUPON(3,"折扣券");

    private int code;

    private String desc;

    CouponType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, CouponType> codeMap;
    private static Map<String, CouponType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (CouponType element : CouponType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static CouponType getCouponType(int code) {

        return codeMap.get(code);
    }

    public static CouponType getCouponType(String desc) {

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
