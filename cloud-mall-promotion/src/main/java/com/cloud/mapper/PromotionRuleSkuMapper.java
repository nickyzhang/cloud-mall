package com.cloud.mapper;

import com.cloud.model.promotion.PromotionRuleSku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface PromotionRuleSkuMapper {

    public List<PromotionRuleSku> findBySkuId(Long skuId);

    public List<PromotionRuleSku> findByRuleId(Long ruleId);

    public int savePromotionRuleSku(PromotionRuleSku promotionRuleSku);

    public int deletePromotionRuleSku(Map<String,Object> map);

    public int deletePromotionRuleSkuBySkuId(Long skuId);

    public int deletePromotionRuleSkuByRuleId(Long ruleId);
}
