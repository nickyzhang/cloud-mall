package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum PromotionChannel {

    ALL(1,"所有平台"),
    PC(2,"主站"),
    APP(3,"App"),
    H5_MALL(4,"H5商城");

    private int code;

    private String desc;

    PromotionChannel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PromotionChannel> codeMap;
    private static Map<String, PromotionChannel> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (PromotionChannel element : PromotionChannel.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static PromotionChannel getPromotionChannel(int code) {

        return codeMap.get(code);
    }

    public static PromotionChannel getPromotionChannel(String desc) {

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
