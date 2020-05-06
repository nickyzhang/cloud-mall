package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum ActivityStatus {

    UNSTART(1,"未开始"),
    ONGOING(2,"进行中"),
    FINISHED(3,"已结束");

    private int code;

    private String desc;

    ActivityStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, ActivityStatus> codeMap;
    private static Map<String, ActivityStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (ActivityStatus element : ActivityStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static ActivityStatus getActivityStatus(int code) {

        return codeMap.get(code);
    }

    public static ActivityStatus getActivityStatus(String desc) {

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
