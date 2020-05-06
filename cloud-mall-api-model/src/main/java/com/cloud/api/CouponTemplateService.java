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

    @RequestMapping(value = "/catalog/couponTemplate/add",method = RequestMethod.POST)
    public ResponseResult save(@RequestBody CouponTemplateVO couponTemplateVO);

    @RequestMapping(value = "/catalog/couponTemplate/update",method = RequestMethod.PUT)
    public ResponseResult update(@RequestBody CouponTemplateVO couponTemplateVO);

    @RequestMapping(value = "/catalog/couponTemplate/delete/{templateId}",method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/deleteAllExpiredTemplateList",method = RequestMethod.POST)
    public ResponseResult deleteAllExpiredTemplateList();

    @RequestMapping(value = "/catalog/couponTemplate/list/{templateId}",method = RequestMethod.GET)
    public ResponseResult<CouponTemplateVO> find(@PathVariable("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListCouponType",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListCouponType(
            @RequestParam("couponType") Long couponType);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListPromotionMethod",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListPromotionMethod(
            @RequestParam("promotionMethod") Long promotionMethod);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListAfterIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListAfterIssueTime(String issueTime);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListBeforeIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListBeforeIssueTime(@RequestParam("issueTime") String issueTime);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListBetweenIssueTime",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListBetweenIssueTime(
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime);

    @RequestMapping(value = "/catalog/couponTemplate/findExpiredTemplateList",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findExpiredTemplateList();

    @RequestMapping(value = "/catalog/couponTemplate/findAll",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findAll();

    @RequestMapping(value = "/catalog/couponTemplate/getReceivedCouponNum",method = RequestMethod.GET)
    public ResponseResult getReceivedCouponNum(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/getUsedCouponNum",method = RequestMethod.GET)
    public ResponseResult getUsedCouponNum(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/audit",method = RequestMethod.POST)
    public ResponseResult auditTemplate(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/reject",method = RequestMethod.POST)
    public ResponseResult rejectTemplate(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/launchCoupon",method = RequestMethod.POST)
    public ResponseResult launchCoupon(@RequestParam("templateId") Long templateId);

    @RequestMapping(value = "/catalog/couponTemplate/findBySkuId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findBySkuId(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListByCatId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListByCatId(@RequestParam("catId") Long catId);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListByBrandId",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findTemplateListByBrandId(@RequestParam("brandId") Long brandId);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateListBySkuIds",method = RequestMethod.POST)
    public ResponseResult<List<CouponTemplate>> findTemplateListBySkuIds(@RequestBody Long[] skuIds);

    @RequestMapping(value = "/catalog/couponTemplate/findAllAvailableTemplateList",method = RequestMethod.GET)
    public ResponseResult<List<CouponTemplate>> findAllAvailableTemplateList(@RequestParam("skuId") Long skuId);

    @RequestMapping(value = "/catalog/couponTemplate/findTemplateByCouponId",method = RequestMethod.GET)
    public ResponseResult<CouponTemplateVO> findTemplateByCouponId(@RequestParam("couponId") Long couponId);
}
