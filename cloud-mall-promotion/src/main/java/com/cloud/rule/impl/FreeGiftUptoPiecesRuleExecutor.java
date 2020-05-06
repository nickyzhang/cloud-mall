package com.cloud.rule.impl;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.rule.RuleExecutor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class FreeGiftUptoPiecesRuleExecutor implements RuleExecutor {

    @Override
    public List<Long> execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        boolean freeGift = rule.isFreeGift();
        if (!freeGift) {
            return null;
        }

        int quantity = rule.getLadderQuantity();
        if (pieces < quantity) {
            return null;
        }

        return rule.getGiftList();
    }
}
