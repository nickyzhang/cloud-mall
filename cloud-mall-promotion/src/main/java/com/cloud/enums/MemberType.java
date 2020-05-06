package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum MemberType {

    ALL(1,"所有用户"),
    NEW_USER(2,"新注册用户"),
    VIP_USER(3,"会员用户"),
    MALE_USER(4,"男用户"),
    FEMALE_USER(5,"女用户");

    private int code;

    private String desc;

    MemberType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, MemberType> codeMap;
    private static Map<String, MemberType> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (MemberType element : MemberType.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static MemberType getMemberType(int code) {

        return codeMap.get(code);
    }

    public static MemberType getMemberType(String desc) {

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
