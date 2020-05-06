package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum PromotionPlatform {

    ALL(1,"所有平台"),
    PC_APP_H5(2,"主站+App+H5商城"),
    PC(3,"主站"),
    APP(4,"App"),
    H5_MALL(5,"H5商城"),
    SMS(6,"短信"),
    EMAIL(7,"邮件");

    private int code;

    private String desc;

    PromotionPlatform(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PromotionPlatform> codeMap;
    private static Map<String, PromotionPlatform> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (PromotionPlatform element : PromotionPlatform.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static PromotionPlatform getPromotionPlatform(int code) {

        return codeMap.get(code);
    }

    public static PromotionPlatform getPromotionPlatform(String desc) {

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
