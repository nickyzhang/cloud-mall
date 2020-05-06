package com.cloud.rule.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class FreeShipUptoPiecesRuleExecutor implements RuleExecutor {

    @Override
    public BigDecimal execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule)  {
        boolean freeship = rule.isFreeShip();
        if (!freeship) {
            return BigDecimal.ZERO;
        }

        int quantity = rule.getLadderQuantity();
        if (pieces < quantity) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(rule.getFreightOff());
    }

}
