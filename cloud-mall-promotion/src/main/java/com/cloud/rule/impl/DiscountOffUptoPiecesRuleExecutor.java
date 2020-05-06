package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DiscountOffUptoPiecesRuleExecutor implements RuleExecutor {
    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取规则中数量
        int quantity = rule.getLadderQuantity();
        // 校验规则数量
        if (pieces <= 0) {
            return BigDecimal.ZERO;
        }

        // 判断是否满足规则中的数量
        if (pieces < quantity) {
            return BigDecimal.ZERO;
        }

        // 根据折扣计算优惠额度
        int discount = rule.getDiscountOff();
        if (discount <= 0) {
            return BigDecimal.ZERO;
        }
        // 总金额- (金额 * 折扣 = 折扣后的额度) = 折扣额度
        BigDecimal afterDiscount = amount.multiply(BigDecimal.valueOf(discount)).divide(BigDecimal.valueOf(100));
        BigDecimal savingAmount = amount.subtract(afterDiscount);
        // 判断优惠额度是否超过了限制
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return savingAmount;
        }
        return savingAmount.compareTo(limit) == 1 ? limit : savingAmount;
    }
}
