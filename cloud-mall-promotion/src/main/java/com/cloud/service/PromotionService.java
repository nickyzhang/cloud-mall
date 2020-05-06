package com.cloud.service;

import java.math.BigDecimal;

public interface PromotionService {

    public <T> T calcActivitySavingAmount(Long activityId, int pieces, BigDecimal amount);

    public <T> T calcCouponSavingAmount(Long templateId, BigDecimal amount);
}
