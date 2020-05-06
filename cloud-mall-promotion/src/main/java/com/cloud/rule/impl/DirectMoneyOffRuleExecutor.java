package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DirectMoneyOffRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取优惠金额
        BigDecimal moneyOff = BigDecimal.valueOf(rule.getMoneyOff());
        // 校验优惠金额的正确性和是否超过金额限制
        if (moneyOff.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return moneyOff;
        }
        return moneyOff.compareTo(limit) == 1 ? limit : moneyOff;
    }
}
