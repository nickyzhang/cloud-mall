package com.cloud.rule;

import com.cloud.dto.promotion.PromotionRuleDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class RuleContext {

    RuleExecutor ruleExecutor;

    public RuleContext(RuleExecutor ruleExecutor) {
        this.ruleExecutor = ruleExecutor;
    }

    public <T> T execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule){

        return this.ruleExecutor.execute(amount,pieces,limit,rule);
    }
}
