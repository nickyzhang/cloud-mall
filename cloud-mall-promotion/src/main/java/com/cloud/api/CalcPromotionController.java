package com.cloud.api;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.enums.UseScope;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.po.promotion.CalculateActivityParam;
import com.cloud.po.promotion.CalculateCouponParam;
import com.cloud.service.CouponCalculator;
import com.cloud.service.PromotionService;
import com.cloud.vo.promotion.CouponCaclVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/promotion")
public class CalcPromotionController {

    @Autowired
    PromotionService promotionService;

    @Autowired
    CouponCalculator couponCalculator;

    @RequestMapping("/getActSavedAmount")
    public ResponseResult<BigDecimal> calcActivityRule(@RequestParam("amount") BigDecimal amount,
                                           @RequestParam("pieces") int pieces,
                                           @RequestParam("activityId") Long activityId) {
        ResponseResult<BigDecimal> result = new ResponseResult<BigDecimal>();
        BigDecimal savingAmount = this.promotionService.calcActivitySavingAmount
                (activityId,pieces,amount);
        return result.success("成功",savingAmount);
    }

    @RequestMapping(value = "/getActListSavedAmount",method = RequestMethod.POST)
    public ResponseResult<BigDecimal> calcActivityRule(@RequestBody CalculateActivityParam param) {
        ResponseResult<BigDecimal> result = new ResponseResult<BigDecimal>();
        BigDecimal savedTotalAmount = BigDecimal.ZERO;
        List<Activity> activityList = param.getActivityList();
        for (Activity activity : activityList) {
            BigDecimal savedAmount = this.promotionService.calcActivitySavingAmount
                    (activity.getActivityId(),param.getPieces(),param.getAmount());
            savedTotalAmount = savedTotalAmount.add(savedAmount);
        }
        return result.success("获取活动列表的节省金额成功",savedTotalAmount);
    }

    @RequestMapping("/getCouponSavedAmount")
    public ResponseResult<BigDecimal> calcActivityRule(@RequestParam("amount") BigDecimal amount,
                                           @RequestParam("templateId") Long templateId) {
        ResponseResult<BigDecimal> result = new ResponseResult<BigDecimal>();
        BigDecimal savingAmount = this.promotionService.calcCouponSavingAmount(templateId,amount);
        return result.success(savingAmount);
    }


    @RequestMapping(value = "/getComposedCouponSavedAmount", method = RequestMethod.POST)
    public ResponseResult<List<CouponCaclVO>> getAvailableCouponInfo(@RequestBody List<CalculateCouponParam> paramList) {
        ResponseResult<List<CouponCaclVO>> result = new ResponseResult<List<CouponCaclVO>>();
        if (CollectionUtils.isEmpty(paramList)) {
            return result.failed("计算优惠券组合的参数为空");
        }

        // 正向构建skuId -> couponList的映射，方便计算
        Map<Long,List<Coupon>> skuCouponListMap = new HashMap<>();
        for (CalculateCouponParam param : paramList) {
            Long skuId = param.getSkuId();
            List<Coupon> couponList = param.getCouponList();
            skuCouponListMap.put(skuId,couponList);
        }
        // 逆向构建coupon->skuList的映射，方便计算
        Map<Coupon,Map<Long,BigDecimal>> couponSkuMap = new HashMap<>();
        for (CalculateCouponParam param : paramList) {
            for (Coupon coupon : param.getCouponList()) {
                Map<Long,BigDecimal> skuInfos = couponSkuMap.get(coupon);
                if (skuInfos == null) {
                    skuInfos = new HashMap<>();
                    skuInfos.put(param.getSkuId(),param.getAmount());
                    couponSkuMap.put(coupon,skuInfos);
                } else if (!skuInfos.containsKey(param.getSkuId())) {
                    skuInfos.put(param.getSkuId(),param.getAmount());
                }
            }
        }
        log.info("[couponSkuMap]: "+JSONObject.toJSONString(couponSkuMap));
        // 计算单品券和品类券和品牌券的优惠，并将结果保存起来
        Map<Long,BigDecimal> couponResult = new HashMap<>();
        for (Map.Entry<Coupon,Map<Long,BigDecimal>> entry : couponSkuMap.entrySet()) {
            Coupon coupon = entry.getKey();
            BigDecimal savedMoney = couponCalculator.calculate(coupon,entry.getValue());
            if (savedMoney.compareTo(BigDecimal.ZERO) > 0) {
                couponResult.put(coupon.getCouponId(), savedMoney);
            }
        }
        List<CouponCaclVO> couponCaclList = new ArrayList<>();
        CouponCaclVO couponCacl = null;
        Set<Long> joinedSkuSet = new HashSet<>();
        Set<Long> joinedCouponSet = new HashSet<>();
        for (Map.Entry<Long,List<Coupon>> entry : skuCouponListMap.entrySet()) {
            Long skuId = entry.getKey();
            BigDecimal maxSavedMoney = BigDecimal.ZERO;
            BigDecimal savedAmount = null;
            Coupon maxSavedCoupon = null;
            for (Coupon coupon : entry.getValue()) {
                log.info("[joinedCouponSet]: "+JSONObject.toJSONString(joinedCouponSet));
                // 如果这个优惠券已经被确认使用那么则判断其他优惠券的SKU ID。如果这个SKU的这个coupon已经包含了
                // 参见过活动的SKU则这个优惠券不再参加，选择其他的
                if (joinedCouponSet.contains(coupon.getCouponId()) || checkCouponList(joinedSkuSet,getJoinCouponSKUList(couponSkuMap.get(coupon)))) {
                    continue;
                }
                savedAmount = couponResult.get(coupon.getCouponId());
                if (savedAmount == null || savedAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                if (savedAmount.compareTo(maxSavedMoney) > 0) {
                    maxSavedMoney = savedAmount;
                    maxSavedCoupon = coupon;
                }
            }
            if (maxSavedCoupon != null) {
                couponCacl = new CouponCaclVO();
                couponCacl.setCoupon(maxSavedCoupon);
                couponCacl.setSavedAmount(couponResult.get(maxSavedCoupon.getCouponId()));
                couponCacl.setSkuList(getJoinCouponSKUList(couponSkuMap.get(maxSavedCoupon)));
                log.info("[couponCacl]: "+JSONObject.toJSONString(couponCacl));
                if (!joinedCouponSet.contains(maxSavedCoupon.getCouponId())) {
                    joinedCouponSet.add(maxSavedCoupon.getCouponId());
                }
                couponCaclList.add(couponCacl);
            }
            log.info("[couponCaclList]: "+JSONObject.toJSONString(couponCaclList));
        }

        return result.success("获取优惠券组合信息成功",couponCaclList);
    }

    /**
     * 获取coupon对应的SKU列表信息，获取SKU id列表
     * @param map
     * @return
     */
    private List<Long> getJoinCouponSKUList(Map<Long,BigDecimal> map) {
        List<Long> list = new ArrayList<>();
        map.forEach((k,v) -> {
            list.add(k);
        });
        return list;
    }

    /**
     * 检查是否存在已经参见过活动的SKU
     * @param boxes
     * @param elements
     * @return
     */
    private boolean checkCouponList(Set<Long> boxes, List<Long> elements) {
        if (CollectionUtils.isEmpty(boxes)) {
            return Boolean.FALSE;
        }
        for (Long skuId : elements) {
            if (boxes.contains(skuId)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
