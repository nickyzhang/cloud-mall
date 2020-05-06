package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum UseScope {

    ALL(1,"无限制"),
    SKU_SCOPE(2,"单品券"),
    BRAND_SCOPE(3,"品牌券"),
    CATEGORY_SCOPE(4,"品类券");

    private int code;

    private String desc;

    UseScope(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, UseScope> codeMap;
    private static Map<String, UseScope> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (UseScope element : UseScope.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static UseScope getUseScope(int code) {

        return codeMap.get(code);
    }

    public static UseScope getUseScope(String desc) {

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
