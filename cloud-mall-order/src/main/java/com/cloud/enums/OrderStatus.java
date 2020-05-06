package com.cloud.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {

    WAITING_PAYMENT(1,"待付款"),
    WAITING_DELIVERY(2,"待发货"),
    WAITING_RECEIVE(3,"待收货"),
    COMPLETED(4,"已完成"),
    CANCELLED(5,"已取消"),
    AFTER_SALES_SERVICING(6,"售后中"),
    CLOSED(7,"已关闭"),
    WAITING_REMARK(8,"待评价"),
    REMAKED(9,"已评价");
    
    private int code;

    private String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<Integer, OrderStatus> codeMap;
    private static Map<String, OrderStatus> descMap;

    static {
        codeMap = new HashMap<>();
        descMap = new HashMap<>();
        for (OrderStatus element : OrderStatus.values()) {
            codeMap.put(element.getCode(), element);
            descMap.put(element.getDesc(), element);
        }
    }

    public static OrderStatus getOrderStatus(int code) {

        return codeMap.get(code);
    }

    public static OrderStatus getOrderStatus(String desc) {

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
