package com.cloud.vo.order;

import com.cloud.BaseModel;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemVO extends BaseModel {

    private static final long serialVersionUID = -4325161722872379905L;

    private Long itemId;

    private Long orderId;

    private Long orderNo;

    private Long skuId;

    private String skuCode;

    private String skuName;

    private String imageUrl;

    private Long productId;

    private Long catId;

    private Long brandId;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal couponAmount;

    private BigDecimal activityAmount;

    private BigDecimal subTotalAmount;
}

