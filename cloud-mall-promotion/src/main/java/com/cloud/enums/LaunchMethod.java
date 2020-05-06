package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum LaunchMethod {

    ONLY_LAUNCH(1,"仅投放"),
    ONLY_RECEIVE(2,"仅领取");

    private int code;

    private String desc;

    LaunchMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, LaunchMethod> codeMap;
    private static Map<String, LaunchMethod> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (LaunchMethod element : LaunchMethod.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static LaunchMethod getLaunchMethod(int code) {

        return codeMap.get(code);
    }

    public static LaunchMethod getLaunchMethod(String desc) {

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
