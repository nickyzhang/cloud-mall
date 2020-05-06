package com.cloud.po.order;

import com.cloud.model.promotion.Coupon;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderReq implements Serializable {

    private Long memberId;

    private Long addressId;

    private String shippingMethod;

    private String source;

    private String type;

    private List<Coupon> couponList;

    private BigDecimal couponAmount;

    private BigDecimal activityAmount;

    private BigDecimal totalAmount;

    private List<OrderItemReq> itemList;
}
