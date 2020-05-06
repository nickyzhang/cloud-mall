package com.cloud.service.impl;

import com.cloud.model.promotion.Coupon;
import com.cloud.service.CouponCalculator;
import com.cloud.service.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CouponCalculatorImpl implements CouponCalculator {

    @Autowired
    PromotionService promotionService;

    @Override
    public BigDecimal calculate(Coupon coupon, Map<Long, BigDecimal> target) {
        Map<Coupon,BigDecimal> result = new HashMap<>();
        Long templateId = coupon.getTemplate().getTemplateId();
        BigDecimal amount = BigDecimal.ZERO;
        for (Map.Entry<Long,BigDecimal> entry : target.entrySet()) {
            amount = amount.add(entry.getValue());
        }
        return this.promotionService.calcCouponSavingAmount(templateId,amount);
    }
}
