package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.PromotionRuleDTO;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.service.PromotionRuleService;
import com.cloud.vo.promotion.PromotionRuleVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PromotionRuleController {

    @Autowired
    PromotionRuleService promotionRuleService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/rule/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加促销规则")
    public ResponseResult save(@RequestBody PromotionRuleVO promotionRuleVO){
        ResponseResult result = new ResponseResult();
        if (promotionRuleVO == null) {
            return result.failed("要保存的促销规则为空");
        }
        PromotionRuleDTO apromotionRuleDTO = BeanUtils.copy(promotionRuleVO, PromotionRuleDTO.class);
        apromotionRuleDTO.setRuleId(this.genIdService.genId());
        apromotionRuleDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        apromotionRuleDTO.setCreateTime(now);
        apromotionRuleDTO.setUpdateTime(now);
        int code = this.promotionRuleService.save(apromotionRuleDTO);
        return code == 0 ? result.failed("保存失败") : result.success("保存成功",null);
    }

    @RequestMapping(value = "/rule/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新促销规则")
    public ResponseResult update(@RequestBody PromotionRuleVO promotionRuleVO){
        ResponseResult result = new ResponseResult();
        if (promotionRuleVO == null) {
            return result.failed("要更新的促销规则为空");
        }
        PromotionRuleDTO apromotionRuleDTO = BeanUtils.copy(promotionRuleVO, PromotionRuleDTO.class);
        int code = this.promotionRuleService.update(apromotionRuleDTO);
        return code == 0 ? result.failed("更新失败") : result.success("更新成功",null);
    }

    @RequestMapping(value = "/rule/delete/{ruleId}",method = RequestMethod.POST)
    @ApiOperation(value = "删除促销规则")
    public ResponseResult delete(@PathVariable("ruleId") Long ruleId){
        ResponseResult result = new ResponseResult();
        if (ruleId == null) {
            return result.failed("要删除促销规则ID为空");
        }
        int code = this.promotionRuleService.delete(ruleId);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/rule/list/{ruleId}",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找促销规则")
    public ResponseResult<PromotionRuleVO> find(@PathVariable("ruleId") Long ruleId){
        ResponseResult result = new ResponseResult();
        if (ruleId == null) {
            return result.failed("要查询促销规则ID参数为空");
        }
        PromotionRuleVO promotionRuleVO = this.promotionRuleService.find(ruleId);
        return promotionRuleVO == null ? result.success("该促销规则不存在",null) : result.success("查询成功",promotionRuleVO);
    }

    @RequestMapping(value = "/rule/findRuleListByType",method = RequestMethod.GET)
    @ApiOperation(value = "根据规则类型查找促销规则")
    public ResponseResult<List<PromotionRule>> findRuleListByType(@RequestParam("ruleType") Integer ruleType){
        ResponseResult result = new ResponseResult();
        if (ruleType == null) {
            return result.failed("促销规则参数为空");
        }
        List<PromotionRule> ruleList = this.promotionRuleService.findRuleListByType(ruleType);
        return CollectionUtils.isEmpty(ruleList) ? result.success("没有数据返回", null) :
                result.success("查询成功", ruleList);
    }

    @RequestMapping(value = "/rule/findRuleByTemplateId",method = RequestMethod.GET)
    @ApiOperation(value = "根据优惠券模板id查找促销规则")
    public ResponseResult<PromotionRuleVO> findRuleByCouponId(@RequestParam("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        if (templateId == null) {
            return result.failed("优惠券模板id查询参数为空");
        }
        PromotionRuleVO promotionRuleVO = this.promotionRuleService.findRuleByTemplateId(templateId);
        return promotionRuleVO == null ? result.success("没有数据返回", null) :
                result.success("查询成功", promotionRuleVO);
    }


    @RequestMapping(value = "/rule/findRuleByActivityId",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动ID查找促销规则")
    public ResponseResult<PromotionRuleVO> findRuleByActivityId(@RequestParam("activityId") Long activityId){
        ResponseResult result = new ResponseResult();
        if (activityId == null) {
            return result.failed("活动ID查询参数为空");
        }
        PromotionRuleVO promotionRuleVO = this.promotionRuleService.findRuleByActivityId(activityId);
        return promotionRuleVO == null ? result.success("没有数据返回", null) :
                result.success("查询成功", promotionRuleVO);
    }

    @RequestMapping(value = "/rule/findAll",method = RequestMethod.GET)
    @ApiOperation(value = "查找所有的促销规则")
    public ResponseResult<List<PromotionRule>> findAll(){
        ResponseResult result = new ResponseResult();
        List<PromotionRule> promotionRuleList = this.promotionRuleService.findAll();
        return CollectionUtils.isEmpty(promotionRuleList) ? result.success("没有数据返回", null) :
                result.success("查询成功", promotionRuleList);
    }
}
