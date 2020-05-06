package com.cloud.dto.order;

import com.cloud.BaseModel;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemDTO extends BaseModel {

    private static final long serialVersionUID = 6922446073403378771L;

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

    List<Activity> activityList;

    List<Coupon> couponList;

    private BigDecimal couponAmount;

    private BigDecimal activityAmount;

    private BigDecimal subTotalAmount;
}
