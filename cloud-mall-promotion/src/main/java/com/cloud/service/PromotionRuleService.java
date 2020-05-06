package com.cloud.service;

import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.model.promotion.PromotionRuleSku;
import com.cloud.vo.promotion.PromotionRuleVO;

import java.util.List;

public interface PromotionRuleService {

    public PromotionRuleVO find(Long ruleId);

    public List<PromotionRule> findRuleListByType(Integer ruleType);

    public PromotionRuleVO findRuleByTemplateId(Long couponId);

    public PromotionRuleVO findRuleByActivityId(Long activityId);

    public List<PromotionRule> findAll();

    public int save(PromotionRuleDTO ruleDTO);

    public int saveList(List<PromotionRule> rule);

    public int update(PromotionRuleDTO ruleDTO);

    public int delete(Long ruleId);
}
