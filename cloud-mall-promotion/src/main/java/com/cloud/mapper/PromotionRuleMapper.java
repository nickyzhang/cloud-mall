package com.cloud.mapper;

import com.cloud.model.promotion.PromotionRule;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PromotionRuleMapper {

    public PromotionRule find(Long ruleId);

    public List<PromotionRule> findRuleListByType(Integer ruleType);

    public PromotionRule findRuleByTemplateId(Long templateId);

    public PromotionRule findRuleByActivityId(Long activityId);

    public List<PromotionRule> findAll();

    public Long count();

    public int save(PromotionRule rule);

    public int saveList(List<PromotionRule> rule);

    public int update(PromotionRule rule);

    public int delete(Long ruleId);
}
