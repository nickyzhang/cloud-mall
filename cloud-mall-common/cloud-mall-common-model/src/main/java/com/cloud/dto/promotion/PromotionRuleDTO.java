package com.cloud.dto.promotion;

import com.cloud.model.promotion.PromotionRule;
import com.cloud.model.promotion.PromotionRuleSku;
import lombok.Data;
import java.util.List;

@Data
public class PromotionRuleDTO extends PromotionRule {

    private static final long serialVersionUID = 5566234542299349112L;

    /** 赠送的礼品列表 */
    private List<PromotionRuleSku> giftList;
}
