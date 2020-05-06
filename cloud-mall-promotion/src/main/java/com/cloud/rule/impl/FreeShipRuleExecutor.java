package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class FreeShipRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        boolean freeship = rule.isFreeShip();
        if (!freeship) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(rule.getFreightOff());
    }
}
