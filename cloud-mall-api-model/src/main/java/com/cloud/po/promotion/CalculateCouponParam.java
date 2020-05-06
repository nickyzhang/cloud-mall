package com.cloud.po.promotion;

import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculateCouponParam implements Serializable {

    private Long skuId;

    private BigDecimal amount;

    private List<Coupon> couponList;


}
