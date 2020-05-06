package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.DateUtils;
import com.cloud.dto.promotion.ActivityDTO;
import com.cloud.enums.PromotionMethod;
import com.cloud.model.catalog.Sku;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.PromotionRule;
import com.cloud.service.ActivityService;
import com.cloud.service.PromotionRuleService;
import com.cloud.vo.promotion.ActivityVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.language.bm.RuleType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @Autowired
    PromotionRuleService promotionRuleService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    GenIdService genIdService;

    @RequestMapping(value = "/act/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加促销活动")
    public ResponseResult save(@RequestBody ActivityVO activityVO){
        ResponseResult result = new ResponseResult();
        if (activityVO == null) {
            return result.failed("要保存的促销活动为空");
        }
        ActivityDTO activityDTO = BeanUtils.copy(activityVO, ActivityDTO.class);
        activityDTO.setActivityId(this.genIdService.genId());
        activityDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        activityDTO.setCreateTime(now);
        activityDTO.setUpdateTime(now);
        int code = this.activityService.save(activityDTO);
        return code == 0 ? result.failed("保存失败") : result.success("保存成功",null);
    }

    @RequestMapping(value = "/act/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新促销活动")
    public ResponseResult update(@RequestBody ActivityVO activityVO){
        ResponseResult result = new ResponseResult();
        if (activityVO == null) {
            return result.failed("要更新的促销活动为空");
        }
        ActivityDTO activityDTO = BeanUtils.copy(activityVO, ActivityDTO.class);
        int code = this.activityService.update(activityDTO);
        return code == 0 ? result.failed("更新失败") : result.success("更新成功",null);
    }

    @RequestMapping(value = "/act/delete/{activityId}",method = RequestMethod.POST)
    @ApiOperation(value = "删除促销活动")
    public ResponseResult delete(@PathVariable("activityId") Long activityId){
        ResponseResult result = new ResponseResult();
        if (activityId == null) {
            return result.failed("要删除活动ID为空");
        }
        int code = this.activityService.delete(activityId);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/act/list/{activityId}",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找促销活动")
    public ResponseResult<ActivityVO> find(@PathVariable("activityId") Long activityId){
        ResponseResult result = new ResponseResult();
        if (activityId == null) {
            return result.failed("要查询活动ID参数为空");
        }
        ActivityVO activityVO = this.activityService.find(activityId);
        return activityVO == null ? result.success("该活动不存在",null) : result.success("查询成功",activityVO);
    }

    @RequestMapping(value = "/act/findBySkuId",method = RequestMethod.GET)
    @ApiOperation(value = "根据sku id查找促销活动")
    public ResponseResult<List<Activity>> findBySkuId(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        if (skuId == null) {
            return result.failed("sku id参数为空");
        }
        List<Activity> activityList = this.activityService.findBySkuId(skuId);
        return CollectionUtils.isEmpty(activityList)? result.success("这个sku id 没有促销活动类型",null) : result.success("查询成功",activityList);
    }

    @RequestMapping(value = "/act/findActsBySkuIds",method = RequestMethod.GET)
    @ApiOperation(value = "根据sku id查找促销活动")
    public ResponseResult<List<Activity>> findActsBySkuIds(@RequestBody Long[] skuIds) {
        ResponseResult result = new ResponseResult();
        if (ArrayUtils.isEmpty(skuIds)) {
            return result.failed("sku id参数数组为空");
        }
        List<Activity> activityList = this.activityService.findActsBySkuIds(skuIds);
        return CollectionUtils.isEmpty(activityList)? result.success("没有这些sku id对应的活动",null) : result.success("查询成功",activityList);
    }

    @RequestMapping(value = "/act/findByActivityType",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动类型查找促销活动")
    public ResponseResult<List<Activity>> findByActivityType(@RequestParam("actType") Integer actType) {
        ResponseResult result = new ResponseResult();
        if (actType == null) {
            return result.failed("要查询活动类型参数为空");
        }
        List<Activity> activityList = this.activityService.findByActivityType(actType);
        return CollectionUtils.isEmpty(activityList)? result.success("不存在该活动类型",null) : result.success("查询成功",activityList);
    }

    @RequestMapping(value = "/act/findActivityListByTimeRange",method = RequestMethod.GET)
    @ApiOperation(value = "根据时间范围查找促销活动")
    public ResponseResult<List<Activity>> findActivityListByTimeRange(
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime){
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(startTime)) {
            return result.failed("要查询开始时间为空");
        }

        if (StringUtils.isBlank(endTime)) {
            return result.failed("要查询结束参数为空");
        }

        List<Activity> activityList = this.activityService.findActivityListByTimeRange(startTime,endTime);
        return CollectionUtils.isEmpty(activityList)? result.success("没有数据返回",null) : result.success("查询成功",activityList);
    }

    @RequestMapping(value = "/act/findAll",method = RequestMethod.GET)
    @ApiOperation(value = "查找所有的促销活动")
    public ResponseResult<List<ActivityVO>> findAll() {
        ResponseResult result = new ResponseResult();
        List<Activity> activityList = this.activityService.findAll();
        return CollectionUtils.isEmpty(activityList)? result.success("没有数据返回",null) : result.success("查询成功",activityList);
    }

    @RequestMapping(value = "/act/count",method = RequestMethod.GET)
    @ApiOperation(value = "查找所有的促销活动数量")
    public ResponseResult count(){
        ResponseResult result = new ResponseResult();
        Long count = this.activityService.count();
        return result.success("查询成功",count);
    }

    static Long[] skuList = {118894019127279619L,118895868110700547L,118896029171974147L,118896221371760643L,118896283648786435L,120093937426759683L,120093938500501507L,120093938500502531L,120093938500503555L,120093938500504579L,120093938500505603L,120093938500506627L,120095254907977731L,120095254907978755L,120095254907979779L,120095254907980803L,120095254907981827L,120095254907982851L,120095254907983875L,120105305131450371L,120105305131451395L,120105305131452419L,120105305131453443L,120105305131454467L,120105306205192195L,120105306205193219L,160824720759981057L,160824720759982081L};
    @RequestMapping(value = "/act/batchAdd",method = RequestMethod.GET)
    public int batchAdd(@RequestParam("limit") int limit) {
        List<PromotionRule> ruleList = this.promotionRuleService.findAll();
        for (int i = 0; i < limit; i++) {
            ActivityVO activityVO = new ActivityVO();
            activityVO.setActivityId(this.genIdService.genId());
            PromotionRule rule = ruleList.get(getNum(0,ruleList.size()-1));
            if (rule.getRuleType() == PromotionMethod.FREE_GIFT_UPTO_PIECES.getCode() ||
                    rule.getRuleType() == PromotionMethod.FREE_GIFT.getCode() ||
                    rule.getRuleType() == PromotionMethod.FREE_GIFT_UPTO_MONEY.getCode()) {
                continue;
            }

            if (rule.getRuleType() == PromotionMethod.MONEY_OFF_UPTO_MONEY.getCode() ||
                    rule.getRuleType() == PromotionMethod.MONEY_OFF_EVERY_SPEND_MONEY.getCode() ||
                    rule.getRuleType() == PromotionMethod.MONEY_OFF_UPTO_PIECES.getCode() ||
                    rule.getRuleType() == PromotionMethod.DIRECT_MONEY_OFF.getCode()) {
                activityVO.setActivityType(1);

            }

            if (rule.getRuleType() == PromotionMethod.DISCOUNT_OFF_UPTO_MONEY.getCode() ||
                    rule.getRuleType() == PromotionMethod.DISCOUNT_OFF_UPTO_PIECES.getCode() ||
                    rule.getRuleType() == PromotionMethod.DIRECT_DISCOUNT_OFF.getCode()) {
                activityVO.setActivityType(2);

            }

            if (rule.getRuleType() == PromotionMethod.FREE_SHIP_UPTO_PIECES.getCode() ||
                    rule.getRuleType() == PromotionMethod.FREE_SHIP.getCode() ||
                    rule.getRuleType() == PromotionMethod.FREE_SHIP_UPTO_MONEY.getCode()) {
                activityVO.setActivityType(6);
            }
            activityVO.setPromotionMethod(rule.getRuleType());
            activityVO.setActivityStatus(getNum(1,3));
            activityVO.setActivityTags(PromotionMethod.getPromotionMethod(activityVO.getPromotionMethod()).getDesc());
            activityVO.setDescription("钜惠大促");
            activityVO.setImageUrl("https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85");
            activityVO.setMemberType(1);
            activityVO.setName("钜惠大促");
            activityVO.setPromotionChannel(getNum(1,4));
            activityVO.setPromotionLink("https://item.cloud.com/national-day.html");
            activityVO.setPromotionPlatform(getNum(1,7));
            activityVO.setQuantityLimit(100);
            activityVO.setStockLimit(getNum(1,99));
            activityVO.setPromotionRule(rule);
            activityVO.setDeleted(false);
            int day = getNum(15,28);
            int hour = getNum(12,23);
            int mintue = getNum(10,59);
            int second = getNum(10,59);
            LocalDateTime time = DateUtils.pasreToDateTime("2019-10-"+day+" "+hour+":"+mintue+":"+second,"yyyy-MM-dd HH:mm:ss");
            activityVO.setCreateTime(time);
            activityVO.setUpdateTime(time);
            activityVO.setStartTime(activityVO.getCreateTime().plusDays(2));
            activityVO.setEndTime(activityVO.getStartTime().plusDays(30));
            int num = getNum(1,3);
            Set<Long> skus = new HashSet<>(num);
            for(;skus.size() <= num - 1;) {
                Long skuId = skuList[getNum(0,skuList.length-1)];
                skus.add(skuId);
            }
            List<Long> selected = new ArrayList<>(skus);
            activityVO.setSkuList(selected);
            this.activityService.save(BeanUtils.copy(activityVO,ActivityDTO.class));
        }
        return 200;
    }

    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

}
