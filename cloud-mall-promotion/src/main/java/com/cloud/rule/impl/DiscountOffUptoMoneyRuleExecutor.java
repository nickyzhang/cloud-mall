package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DiscountOffUptoMoneyRuleExecutor implements RuleExecutor {
    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取规则的阶梯满减额度
        BigDecimal ladderSpendMoney = BigDecimal.valueOf(rule.getLadderSpendMoney());
        // 规则阶梯满减额度和待计算的金额进行比较，看是否达到优惠条件
        // 如果不满足额度，则返回0
        if (amount.compareTo(ladderSpendMoney) < 0) {
            return BigDecimal.ZERO;
        }

        // 根据折扣计算优惠额度
        int discount = rule.getDiscountOff();
        if (discount <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal afterDiscount = amount.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100));
        BigDecimal savingAmount = amount.subtract(afterDiscount);

        // 判断优惠额度是否超过了限制
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return savingAmount;
        }
        return savingAmount.compareTo(limit) == 1 ? limit : savingAmount;
    }
}
