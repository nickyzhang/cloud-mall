package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum ServiceApplyType {

    RETURN_GOODS(1,"退货"),
    EXCHANGE_GOODS(2,"换货");

    private int code;

    private String desc;

    ServiceApplyType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ServiceApplyType> codeMap;
    private static Map<String, ServiceApplyType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (ServiceApplyType element : ServiceApplyType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static ServiceApplyType getServiceApplyType(int code) {

        return codeMap.get(code);
    }

    public static ServiceApplyType getServiceApplyType(String desc) {

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
