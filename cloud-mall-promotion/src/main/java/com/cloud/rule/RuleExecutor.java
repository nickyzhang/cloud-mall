package com.cloud.rule;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import java.math.BigDecimal;

/** 规则执行接口 */
public interface RuleExecutor {

    /**
     * 根据金额、优惠限制额度和规则计算优惠金额
     * @param amount 待计算的金额
     * @param pieces 商品数量
     * @param limit  优惠限制额度，防止过度优惠
     * @param rule   优惠规则
     * @return
     */
    public <T> T execute(BigDecimal amount, Integer pieces, BigDecimal limit, PromotionRuleDTO rule);
}
