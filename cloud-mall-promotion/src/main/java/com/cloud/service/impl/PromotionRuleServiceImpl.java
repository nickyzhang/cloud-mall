package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.model.promotion.PromotionRuleSku;
import com.cloud.mapper.PromotionRuleMapper;
import com.cloud.mapper.PromotionRuleSkuMapper;
import com.cloud.service.PromotionRuleService;
import com.cloud.vo.promotion.PromotionRuleVO;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class PromotionRuleServiceImpl implements PromotionRuleService {

    @Autowired
    PromotionRuleMapper ruleMapper;

    @Autowired
    PromotionRuleSkuMapper ruleSkuMapper;

    @Override
    public PromotionRuleVO find(Long ruleId) {
        PromotionRule rule = this.ruleMapper.find(ruleId);
        if (rule == null) {
            return null;
        }

        PromotionRuleDTO ruleDTO = BeanUtils.copy(rule,PromotionRuleDTO.class);
        List<PromotionRuleSku> ruleSkuList = this.ruleSkuMapper.findByRuleId(ruleId);
        List<Long> giftList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(ruleSkuList)) {
            ruleSkuList.forEach(elements -> {
                giftList.add(elements.getSkuId());
            });
            ruleDTO.setGiftList(giftList);
        }
        return BeanUtils.copy(ruleDTO,PromotionRuleVO.class);
    }

    @Override
    public List<PromotionRule> findRuleListByType(Integer ruleType) {
        return this.ruleMapper.findRuleListByType(ruleType);

    }

    @Override
    public PromotionRuleVO findRuleByTemplateId(Long templateId) {
        PromotionRule rule =  this.ruleMapper.findRuleByTemplateId(templateId);
        if (rule == null) {
            return null;
        }

        PromotionRuleDTO ruleDTO = BeanUtils.copy(rule,PromotionRuleDTO.class);
        List<PromotionRuleSku> ruleSkuList = this.ruleSkuMapper.findByRuleId(rule.getRuleId());
        List<Long> giftList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(giftList)) {
            ruleSkuList.forEach(elements -> {
                giftList.add(elements.getSkuId());
            });
            ruleDTO.setGiftList(giftList);
        }
        return BeanUtils.copy(ruleDTO,PromotionRuleVO.class);
    }

    @Override
    public PromotionRuleVO findRuleByActivityId(Long activityId) {
        PromotionRule rule =  this.ruleMapper.findRuleByActivityId(activityId);
        if (rule == null) {
            return null;
        }

        PromotionRuleDTO ruleDTO = BeanUtils.copy(rule,PromotionRuleDTO.class);
        List<PromotionRuleSku> ruleSkuList = this.ruleSkuMapper.findByRuleId(rule.getRuleId());
        List<Long> giftList = new ArrayList<>(10);
        if (CollectionUtils.isNotEmpty(giftList)) {
            ruleSkuList.forEach(elements -> {
                giftList.add(elements.getSkuId());
            });
            ruleDTO.setGiftList(giftList);
        }
        return BeanUtils.copy(ruleDTO,PromotionRuleVO.class);
    }

    @Override
    public List<PromotionRule> findAll() {
        return this.ruleMapper.findAll();
    }

    @Override
    public int save(PromotionRuleDTO ruleDTO) {
        if (ruleDTO == null) {
            return 0;
        }

        PromotionRule rule = BeanUtils.copy(ruleDTO,PromotionRule.class);
        int code = this.ruleMapper.save(rule);
        if (code == 0) {
            return 0;
        }
        List<Long> giftList = ruleDTO.getGiftList();
        if (CollectionUtils.isNotEmpty(giftList)) {
            PromotionRuleSku ruleSku = null;
            for (Long skuId : giftList) {
                ruleSku = new PromotionRuleSku();
                ruleSku.setSkuId(skuId);
                ruleSku.setRuleId(rule.getRuleId());
                ruleSku.setDeleted(false);
                ruleSku.setCreateTime(LocalDateTime.now());
                ruleSku.setUpdateTime(LocalDateTime.now());
                code = this.ruleSkuMapper.savePromotionRuleSku(ruleSku);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    @Override
    public int saveList(List<PromotionRule> ruleList) {
        return this.ruleMapper.saveList(ruleList);
    }

    @Override
    public int update(PromotionRuleDTO ruleDTO) {
        if (ruleDTO == null) {
            return 0;
        }

        PromotionRule rule = BeanUtils.copy(ruleDTO,PromotionRule.class);
        int code = this.ruleMapper.update(rule);
        if (code == 0) {
            return 0;
        }

        List<PromotionRuleSku> ruleSkuList = this.ruleSkuMapper.findByRuleId(rule.getRuleId());
        if (CollectionUtils.isNotEmpty(ruleSkuList)) {
            Set<Long> oldGiftSet = new HashSet<>();
            for (PromotionRuleSku gift : ruleSkuList) {
                oldGiftSet.add(gift.getSkuId());
            }

            Set<Long> newGiftSet = new HashSet<>(ruleDTO.getGiftList());

            Set<Long> deleteGiftSet = Sets.difference(oldGiftSet,newGiftSet);
            for (Long deleteGiftId : deleteGiftSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("ruleId",rule.getRuleId());
                map.put("skuId",deleteGiftId);
                code = this.ruleSkuMapper.deletePromotionRuleSku(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addGiftSet = Sets.difference(newGiftSet,oldGiftSet);
            for (Long addGiftId : addGiftSet) {
                PromotionRuleSku gift = new PromotionRuleSku();
                gift.setSkuId(addGiftId);
                gift.setRuleId(rule.getRuleId());
                gift.setDeleted(false);
                gift.setCreateTime(LocalDateTime.now());
                gift.setUpdateTime(LocalDateTime.now());
                code = this.ruleSkuMapper.savePromotionRuleSku(gift);
                if (code == 0) {
                    return 0;
                }
            }
        }
        return code;
    }

    @Override
    public int delete(Long ruleId) {
        return this.ruleMapper.delete(ruleId);
    }
}
