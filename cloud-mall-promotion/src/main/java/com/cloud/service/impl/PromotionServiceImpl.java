package com.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.enums.PromotionMethod;
import com.cloud.exception.PromotionBizException;
import com.cloud.mapper.ActivityMapper;
import com.cloud.mapper.CouponTemplateMapper;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.rule.RuleContext;
import com.cloud.rule.impl.*;
import com.cloud.service.PromotionRuleService;
import com.cloud.service.PromotionService;
import com.cloud.vo.promotion.PromotionRuleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Slf4j
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    CouponTemplateMapper couponTemplateMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    PromotionRuleService promotionRuleService;

    @Override
    public <T> T calcActivitySavingAmount(Long activityId, int pieces, BigDecimal amount) {
        if (activityId == null) {
            throw new PromotionBizException("促销活动id为空");
        }
        Activity activity = activityMapper.find(activityId);
        if (activityId == null) {
            throw new PromotionBizException("不存在这个促销活动");
        }
        // 这个是满件减金额，但是对应的rule确实满件减折扣
        int promotionMethod = activity.getPromotionMethod();
        PromotionRuleVO promotionRuleVO = promotionRuleService.findRuleByActivityId(activityId);
        PromotionRuleDTO ruleDTO = BeanUtils.copy(promotionRuleVO,PromotionRuleDTO.class);
        return cacl(promotionMethod,amount,pieces,null,ruleDTO);
    }

    @Override
    public <T> T calcCouponSavingAmount(Long templateId,  BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PromotionBizException("非法的金额参数");
        }
        if (templateId == null) {
            throw new PromotionBizException("优惠券模板id为空");
        }
        CouponTemplate template = couponTemplateMapper.find(templateId);
        int promotionMethod = template.getPromotionMethod();
        PromotionRuleVO promotionRuleVO = promotionRuleService.findRuleByTemplateId(templateId);
        PromotionRuleDTO ruleDTO = BeanUtils.copy(promotionRuleVO,PromotionRuleDTO.class);
        BigDecimal limit = template.getAmountLimit();
        return cacl(promotionMethod,amount,0,limit,ruleDTO);
    }


    private <T> T cacl(int promotionMethod,BigDecimal amount,int pieces, BigDecimal limit, PromotionRuleDTO rule){
        RuleContext context = null;

        if (PromotionMethod.DIRECT_MONEY_OFF.getCode() == promotionMethod) {
            context = new RuleContext(new DirectMoneyOffRuleExecutor());
        } else if (PromotionMethod.DIRECT_DISCOUNT_OFF.getCode() == promotionMethod ){
            context = new RuleContext(new DirectDiscountOffRuleExecutor());
        } else if (PromotionMethod.MONEY_OFF_UPTO_MONEY.getCode() == promotionMethod ){
            context = new RuleContext(new MoneyOffUptoMoneyRuleExecutor());
        } else if (PromotionMethod.MONEY_OFF_EVERY_SPEND_MONEY.getCode() == promotionMethod ){
            context = new RuleContext(new MoneyOffEverySpendMoneyRuleExecutor());
        } else if (PromotionMethod.DISCOUNT_OFF_UPTO_MONEY.getCode() == promotionMethod ){
            context = new RuleContext(new DiscountOffUptoMoneyRuleExecutor());
        } else if (PromotionMethod.MONEY_OFF_UPTO_PIECES.getCode() == promotionMethod ){
            context = new RuleContext(new MoneyOffUptoPiecesRuleExecutor());
        } else if (PromotionMethod.DISCOUNT_OFF_UPTO_PIECES.getCode() == promotionMethod ){
            context = new RuleContext(new DiscountOffUptoPiecesRuleExecutor());
        } else if (PromotionMethod.FREE_SHIP.getCode() == promotionMethod ){
            context = new RuleContext(new FreeShipRuleExecutor());
        } else if (PromotionMethod.FREE_SHIP_UPTO_MONEY.getCode() == promotionMethod ){
            context = new RuleContext(new FreeShipUptoMoneyRuleExecutor());
        } else if (PromotionMethod.FREE_SHIP_UPTO_PIECES.getCode() == promotionMethod ){
            context = new RuleContext(new FreeShipUptoPiecesRuleExecutor());
        } else if (PromotionMethod.FREE_GIFT.getCode() == promotionMethod ){
            context = new RuleContext(new FreeGiftRuleExecutor());
        } else if (PromotionMethod.FREE_GIFT_UPTO_MONEY.getCode() == promotionMethod ){
            context = new RuleContext(new FreeGiftUptoMoneyRuleExecutor());
        } else if (PromotionMethod.FREE_GIFT_UPTO_PIECES.getCode() == promotionMethod ){
            context = new RuleContext(new FreeGiftUptoPiecesRuleExecutor());
        }
        if (context == null) {
            return null;
        }
        return context.execute(amount,pieces,limit,rule);
    }
}
