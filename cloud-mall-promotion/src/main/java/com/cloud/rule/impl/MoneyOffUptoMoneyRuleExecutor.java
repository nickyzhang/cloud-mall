package com.cloud.rule.impl;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MoneyOffUptoMoneyRuleExecutor implements RuleExecutor {
    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取阶梯满减额
        BigDecimal ladderSpendMoney = BigDecimal.valueOf(rule.getLadderSpendMoney());

        // 判断待扣减额度是否可用进行扣减，即是否满足优惠条件,如果不满足额度，则返回0
        if (amount.compareTo(ladderSpendMoney) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal moneyOff = BigDecimal.valueOf(rule.getMoneyOff());
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return moneyOff;
        }
        return moneyOff.compareTo(limit) > 0 ? limit : moneyOff;
    }
}
