package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.model.promotion.Coupon;
import com.cloud.po.promotion.CalculateActivityParam;
import com.cloud.po.promotion.CalculateCouponParam;
import com.cloud.vo.promotion.CouponCaclVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(name="cloud-mall-promotion")
public interface PromotionService {

    @RequestMapping(value = "/catalog/promotion/getActSavedAmount",method = RequestMethod.GET)
    public ResponseResult<BigDecimal> getActSavedAmount(@RequestParam("amount") BigDecimal amount,
                                           @RequestParam("pieces") int pieces,
                                           @RequestParam("activityId") Long activityId);

    @RequestMapping(value = "/catalog/promotion/getActListSavedAmount",method = RequestMethod.POST)
    public ResponseResult<BigDecimal> getActListSavedAmount(@RequestBody CalculateActivityParam param);

    @RequestMapping(value = "/catalog/promotion/getCouponSavedAmount",method = RequestMethod.GET)
    public ResponseResult<BigDecimal> getCouponSavedAmount(@RequestParam("amount") BigDecimal amount,
                                           @RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/promotion/getComposedCouponSavedAmount", method = RequestMethod.POST)
    public ResponseResult<List<CouponCaclVO>> getAvailableCouponInfo(@RequestBody List<CalculateCouponParam> paramList);


}
