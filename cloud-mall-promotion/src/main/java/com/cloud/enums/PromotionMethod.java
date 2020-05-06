package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum PromotionMethod {

    DIRECT_MONEY_OFF(1,"直减"),
    DIRECT_DISCOUNT_OFF(2,"直折"),
    MONEY_OFF_UPTO_MONEY(3,"满额减"),
    MONEY_OFF_EVERY_SPEND_MONEY(4,"每满减"),
    DISCOUNT_OFF_UPTO_MONEY(5,"满额折"),
    MONEY_OFF_UPTO_PIECES(6,"满件减"),
    DISCOUNT_OFF_UPTO_PIECES(7,"满件折"),
    FREE_SHIP(8,"免运费"),
    FREE_SHIP_UPTO_MONEY(9,"满额免"),
    FREE_SHIP_UPTO_PIECES(10,"满件免"),
    FREE_GIFT(11,"送赠品"),
    FREE_GIFT_UPTO_MONEY(12,"满额赠"),
    FREE_GIFT_UPTO_PIECES(13,"满件赠");

    private int code;

    private String desc;

    PromotionMethod(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, PromotionMethod> codeMap;
    private static Map<String, PromotionMethod> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (PromotionMethod element : PromotionMethod.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static PromotionMethod getPromotionMethod(int code) {

        return codeMap.get(code);
    }

    public static PromotionMethod getPromotionMethod(String desc) {

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
