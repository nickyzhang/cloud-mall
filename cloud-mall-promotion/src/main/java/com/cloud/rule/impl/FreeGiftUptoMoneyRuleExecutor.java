package com.cloud.rule.impl;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.mapper.PromotionRuleSkuMapper;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.model.promotion.PromotionRuleSku;
import com.cloud.rule.RuleExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class FreeGiftUptoMoneyRuleExecutor implements RuleExecutor {

    @Override
    public List<Long> execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule) {
        boolean freeGift = rule.isFreeGift();
        if (!freeGift) {
            return null;
        }

        BigDecimal ladderSpendMoney = BigDecimal.valueOf(rule.getLadderSpendMoney());
        if (amount.compareTo(ladderSpendMoney) == -1) {
            return null;
        }

        return rule.getGiftList();
    }
}
