package com.cloud.rule.impl;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MoneyOffUptoPiecesRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule){
        // 获取规则中数量
        int quantity = rule.getLadderQuantity();
        // 校验商品数量
        if (pieces <= 0) {
            return BigDecimal.ZERO;
        }

        // 判断是否满足规则中的数量
        if (pieces < quantity) {
            return BigDecimal.ZERO;
        }

        // 获取优惠额度,并且和优惠限制额度进行比较

        BigDecimal moneyOff = BigDecimal.valueOf(rule.getMoneyOff());
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return moneyOff;
        }
        return moneyOff.compareTo(limit) == 1 ? limit : moneyOff;
    }
}
