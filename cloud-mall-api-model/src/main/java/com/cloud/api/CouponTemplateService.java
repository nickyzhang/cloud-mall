package com.cloud.api;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.promotion.CouponTemplateDTO;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.vo.promotion.CouponTemplateVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name="cloud-mall-promotion")
public interface CouponTemplateService {

    @RequestMapping(value = "/couponTemplate/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody CouponTemplateVO couponTemplateVO);

    @RequestMapping(value = "/couponTemplate/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody CouponTemplateVO couponTemplateVO);

    @RequestMapping(value = "/couponTemplate/delete/{templateId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/deleteAllExpiredTemplateList",method = RequestMethod.POST)
    public ResponseResult deleteAllExpiredTemplateList();

    @RequestMapping(value = "/couponTemplate/list/{templateId}",method = RequestMethod.GET)
    public ResponseResult<CouponTemplateVO> find(@PathVariable("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/findTemplateListCouponType",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListCouponType(
            @RequestParam("couponType") Long couponType);

    @RequestMapping(value = "/couponTemplate/findTemplateListPromotionMethod",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListPromotionMethod(
            @RequestParam("promotionMethod") Long promotionMethod);

    @RequestMapping(value = "/couponTemplate/findTemplateListAfterIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListAfterIssueTime(String issueTime);

    @RequestMapping(value = "/couponTemplate/findTemplateListBeforeIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListBeforeIssueTime(@RequestParam("issueTime") String issueTime);

    @RequestMapping(value = "/couponTemplate/findTemplateListBetweenIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListBetweenIssueTime(
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime);

    @RequestMapping(value = "/couponTemplate/findExpiredTemplateList",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findExpiredTemplateList();

    @RequestMapping(value = "/couponTemplate/findAll",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findAll();

    @RequestMapping(value = "/couponTemplate/getReceivedCouponNum",method = RequestMethod.GET)
    public ResponseResult getReceivedCouponNum(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/getUsedCouponNum",method = RequestMethod.GET)
    public ResponseResult getUsedCouponNum(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/audit",method = RequestMethod.POST)
    public ResponseResult auditTemplate(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/reject",method = RequestMethod.POST)
    public ResponseResult rejectTemplate(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/launchCoupon",method = RequestMethod.POST)
    public ResponseResult launchCoupon(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/couponTemplate/findBySkuId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findBySkuId(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/couponTemplate/findTemplateListByCatId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListByCatId(@RequestParam("catId") Long catId);

    @RequestMapping(value = "/couponTemplate/findTemplateListByBrandId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListByBrandId(@RequestParam("brandId") Long brandId);

    @RequestMapping(value = "/couponTemplate/findTemplateListBySkuIds",method = RequestMethod.POST)
    public ResponseResult<List<CouponTemplate>> findTemplateListBySkuIds(@RequestBody Long[] skuIds);

    @RequestMapping(value = "/couponTemplate/findAllAvailableTemplateList",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findAllAvailableTemplateList(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/couponTemplate/findTemplateByCouponId",method = RequestMethod.GET)
    public ResponseResult<CouponTemplateVO> findTemplateByCouponId(@RequestParam("couponId") Long couponId);
}
