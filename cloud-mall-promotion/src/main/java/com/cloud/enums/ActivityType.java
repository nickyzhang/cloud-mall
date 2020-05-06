package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum ActivityType {

    MONEY_OFF(1,"满减促销"),
    DISCOUNT_OFF(2,"折扣促销"),
    FREE_GFIT(3,"赠品促销"),
    GIFT_OFF(4,"满赠促销"),
    MORE_BUY(5,"多买优惠"),
    FREE_SHIP(6,"免运费促销");

    private int code;

    private String desc;

    ActivityType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ActivityType> codeMap;
    private static Map<String, ActivityType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (ActivityType element : ActivityType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static ActivityType getActivityType(int code) {

        return codeMap.get(code);
    }

    public static ActivityType getActivityType(String desc) {

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
