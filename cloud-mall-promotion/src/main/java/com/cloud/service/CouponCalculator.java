package com.cloud.service;

import com.cloud.model.promotion.Coupon;
import java.math.BigDecimal;
import java.util.Map;

public interface CouponCalculator {

    public BigDecimal calculate(Coupon coupon, Map<Long,BigDecimal> target);
}
