package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class MoneyOffEverySpendMoneyRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        // 获取满减额
        BigDecimal perSpendMoney = BigDecimal.valueOf(rule.getPerSpendMoney());
        // 规则每满减额度和待计算的金额进行比较，看是否达到优惠条件
        // 如果不满足额度，则返回0
        if (amount.compareTo(perSpendMoney) < 0) {
            return BigDecimal.ZERO;
        }
        // 获取当前待计算的金额
        BigDecimal remainingAmount = amount;
        // 初始化节约的额度(促销额度)
        BigDecimal savingAmount = BigDecimal.ZERO;

        // 获取规则的满减额度
        BigDecimal moneyOff = BigDecimal.valueOf(rule.getMoneyOff());
        // 如果只要剩余额度大于满减额度，就可以继续减
        while (remainingAmount.compareTo(perSpendMoney) >= 0) {
            savingAmount = savingAmount.add(moneyOff);
            remainingAmount = remainingAmount.subtract(perSpendMoney);
        }
        // 和优惠限制进行比较，超过限制额度
        if (limit == null || limit.compareTo(BigDecimal.ZERO) == 0) {
            return savingAmount;
        }
        return savingAmount.compareTo(limit) == 1 ? limit : savingAmount;
    }
}
