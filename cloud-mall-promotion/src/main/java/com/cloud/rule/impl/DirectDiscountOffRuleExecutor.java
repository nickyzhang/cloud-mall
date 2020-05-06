package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DirectDiscountOffRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取规则中的折扣信息
        int discount = rule.getDiscountOff();
        // 对折扣进行校验
        if (discount <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal savingAmount = amount.subtract(amount.multiply(BigDecimal.valueOf(discount/100)));
        // 优惠额度和优惠限制进行比较，返回最小值
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return savingAmount;
        }
        return savingAmount.compareTo(limit) == 1 ? limit : savingAmount;
    }
}
