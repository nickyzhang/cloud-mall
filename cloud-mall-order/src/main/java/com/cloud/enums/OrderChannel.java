package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderChannel {
    
    DESKTOP(1,"PC"),
    MOBILE(2,"手机"),
    TABLET(3,"平板");

    private int code;

    private String desc;

    OrderChannel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderChannel> codeMap;
    private static Map<String, OrderChannel> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (OrderChannel element : OrderChannel.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static OrderChannel getOrderChannel(int code) {

        return codeMap.get(code);
    }

    public static OrderChannel getOrderChannel(String desc) {

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
