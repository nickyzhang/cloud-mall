package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum TemplateStatus {

    NEW(1,"已新建"),
    AUDITED(2,"已审核"),
    REJECTED(3,"已拒绝"),
    LAUNCHING(4,"发放中"),
    LAUNCHED(5,"已发放"),
    OFF(6,"已下架");

    private int code;

    private String desc;

    TemplateStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, TemplateStatus> codeMap;
    private static Map<String, TemplateStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (TemplateStatus element : TemplateStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static TemplateStatus getCouponStatus(int code) {

        return codeMap.get(code);
    }

    public static TemplateStatus getCouponStatus(String desc) {

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
