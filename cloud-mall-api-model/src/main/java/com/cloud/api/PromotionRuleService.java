package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.vo.promotion.PromotionRuleVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface PromotionRuleService {

    @RequestMapping(value = "/rule/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody PromotionRuleVO promotionRuleVO);

    @RequestMapping(value = "/rule/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody PromotionRuleVO promotionRuleVO);

    @RequestMapping(value = "/rule/delete/{ruleId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("ruleId") Long ruleId);

    @RequestMapping(value = "/rule/list/{ruleId}",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> find(@PathVariable("ruleId") Long ruleId);

    @RequestMapping(value = "/rule/findRuleListByType",method = RequestMethod.GET)
    public ResponseResult<List<PromotionRule>> findRuleListByType(@RequestParam("ruleType") Integer ruleType);

    @RequestMapping(value = "/rule/findRuleByCouponId",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> findRuleByCouponId(@RequestParam("couponId") Long couponId);

    @RequestMapping(value = "/rule/findRuleByActivityId",method = RequestMethod.GET)
    public ResponseResult<PromotionRuleVO> findRuleByActivityId(@RequestParam("activityId") Long activityId);

    @RequestMapping(value = "/rule/findAll",method = RequestMethod.GET)
    public ResponseResult<List<PromotionRule>> findAll();
}
