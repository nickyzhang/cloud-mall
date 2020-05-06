package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.vo.promotion.PromotionRuleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface PromotionRuleService {

    @RequestMapping(value = "/catalog/rule/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PromotionRuleVO promotionRuleVO);

    @RequestMapping(value = "/catalog/rule/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody PromotionRuleVO promotionRuleVO);

    @RequestMapping(value = "/catalog/rule/delete/{ruleId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("ruleId") Long ruleId);

    @RequestMapping(value = "/catalog/rule/list/{ruleId}",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> find(@PathVariable("ruleId") Long ruleId);

    @RequestMapping(value = "/catalog/rule/findRuleListByType",method = RequestMethod.GET)
    public ResponseResult<List<PromotionRule>> findRuleListByType(@RequestParam("ruleType") Integer ruleType);

    @RequestMapping(value = "/catalog/rule/findRuleByCouponId",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> findRuleByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/catalog/rule/findRuleByActivityId",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> findRuleByActivityId(@RequestParam("activityId") Long activityId);

    @RequestMapping(value = "/catalog/rule/findAll",method = RequestMethod.GET)
    public ResponseResult<List<PromotionRule>> findAll();
}
