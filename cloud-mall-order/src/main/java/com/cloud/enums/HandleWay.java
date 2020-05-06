package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum HandleWay {

    PUTAWAY(1,"退货入库"),
    REDELIVERY(2,"重新发货"),
    REFUND(3,"退款"),
    RECLAIM_REDELIVERY(4,"不要求归还并重新发货"),
    COMPENSATION(5,"不退货但是补偿");

    private int code;

    private String desc;

    HandleWay(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, HandleWay> codeMap;
    private static Map<String, HandleWay> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (HandleWay element : HandleWay.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static HandleWay getHandleWay(int code) {

        return codeMap.get(code);
    }

    public static HandleWay getHandleWay(String desc) {

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
