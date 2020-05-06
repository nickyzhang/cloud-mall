package com.cloud.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.common.core.utils.BeanUtils;
import com.cloud.common.core.utils.JSONUtils;
import com.cloud.dto.promotion.CouponTemplateDTO;
import com.cloud.enums.CouponStatus;
import com.cloud.enums.TemplateStatus;
import com.cloud.exception.CouponTemplateBizException;
import com.cloud.model.catalog.Product;
import com.cloud.model.catalog.Sku;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.service.CouponTemplateService;
import com.cloud.vo.catalog.SkuVo;
import com.cloud.vo.promotion.CouponTemplateVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CouponTemplateController {

    @Autowired
    CouponTemplateService couponTemplateService;

    @Autowired
    GenIdService genIdService;

    @Autowired
    CatalogService catalogService;

    @RequestMapping(value = "/couponTemplate/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加优惠券模板")
    public ResponseResult save(@RequestBody CouponTemplateVO couponTemplateVO){
        ResponseResult result = new ResponseResult();
        if (couponTemplateVO == null) {
            return result.failed("要保存的优惠券模板为空");
        }
        CouponTemplateDTO couponTemplateDTO = BeanUtils.copy(couponTemplateVO, CouponTemplateDTO.class);
        couponTemplateDTO.setTemplateId(this.genIdService.genId());
        couponTemplateDTO.setTemplateNo(this.genIdService.genId());
        couponTemplateDTO.setDeleted(Boolean.FALSE);
        LocalDateTime now = LocalDateTime.now();
        couponTemplateDTO.setCreateTime(now);
        couponTemplateDTO.setUpdateTime(now);
        int code = this.couponTemplateService.save(couponTemplateDTO);
        return code == 0 ? result.failed("添加优惠券模板失败"):result.success("添加成功",null);
    }

    @RequestMapping(value = "/couponTemplate/update",method = RequestMethod.PUT)
    @ApiOperation(value = "更新优惠券模板")
    public ResponseResult update(@RequestBody CouponTemplateVO couponTemplateVO){
        ResponseResult result = new ResponseResult();
        if (couponTemplateVO == null) {
            return result.failed("要更新的优惠券模板为空");
        }
        CouponTemplateDTO couponTemplateDTO = BeanUtils.copy(couponTemplateVO, CouponTemplateDTO.class);
        int code = this.couponTemplateService.update(couponTemplateDTO);
        return code == 0 ? result.failed("更新失败") : result.success("更新成功",null);
    }

    @RequestMapping(value = "/couponTemplate/delete/{templateId}",method = RequestMethod.POST)
    @ApiOperation(value = "删除优惠券模板")
    public ResponseResult delete(@PathVariable("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        if (templateId == null) {
            return result.failed("要删除优惠券模板ID为空");
        }
        int code = this.couponTemplateService.delete(templateId);
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/couponTemplate/deleteAllExpiredTemplateList",method = RequestMethod.POST)
    @ApiOperation(value = "删除优惠券模板")
    public ResponseResult deleteAllExpiredTemplateList(){
        ResponseResult result = new ResponseResult();
        int code = this.couponTemplateService.deleteAllExpiredTemplateList();
        return code == 0 ? result.failed("删除失败") : result.success("删除成功",null);
    }

    @RequestMapping(value = "/couponTemplate/list/{templateId}",method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查找优惠券模板")
    public ResponseResult<CouponTemplateVO> find(@PathVariable("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        if (templateId == null) {
            return result.failed("要查询优惠券模板ID参数为空");
        }
        CouponTemplateVO couponTemplateVO = this.couponTemplateService.find(templateId);
        return couponTemplateVO == null ? result.success("该优惠券模板不存在",null) :
                result.success("查询成功",couponTemplateVO);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListCouponType",method = RequestMethod.GET)
    @ApiOperation(value = "根据模板类型查找优惠券")
    public ResponseResult<List<CouponTemplate>> findTemplateListCouponType(@RequestParam("couponType") Long couponType){
        ResponseResult result = new ResponseResult();
        if (couponType == null) {
            return result.failed("优惠券模板类型参数为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListCouponType(couponType);
        return CollectionUtils.isEmpty(templateList) ? result.success("没有这个类型的优惠券模板",null) :
                result.success("查询成功",templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListPromotionMethod",method = RequestMethod.GET)
    @ApiOperation(value = "根据促销方式查找优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListPromotionMethod(@RequestParam("promotionMethod") Long promotionMethod){
        ResponseResult result = new ResponseResult();
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListPromotionMethod(promotionMethod);
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListAfterIssueTime",method = RequestMethod.GET)
    @ApiOperation(value = "查找指定发行时间之后的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListAfterIssueTime(String issueTime){
        ResponseResult result = new ResponseResult();
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListAfterIssueTime(issueTime);
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListBeforeIssueTime",method = RequestMethod.GET)
    @ApiOperation(value = "查找指定发行时间之前的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListBeforeIssueTime(@RequestParam("issueTime") String issueTime){
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(issueTime)) {
            return result.failed("指定的发行时间为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListBeforeIssueTime(issueTime);
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListBetweenIssueTime",method = RequestMethod.GET)
    @ApiOperation(value = "查找指定时间范围内的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListBetweenIssueTime(
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime){
        ResponseResult result = new ResponseResult();
        if (StringUtils.isBlank(startTime)) {
            return result.failed("要查询开始时间为空");
        }

        if (StringUtils.isBlank(endTime)) {
            return result.failed("要查询结束参数为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListBetweenIssueTime(startTime,endTime);
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/findExpiredTemplateList",method = RequestMethod.GET)
    @ApiOperation(value = "查找已经到期的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findExpiredTemplateList(){
        ResponseResult result = new ResponseResult();
        List<CouponTemplate> templateList = this.couponTemplateService.findExpiredTemplateList();
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/findAll",method = RequestMethod.GET)
    @ApiOperation(value = "查找所有类型的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findAll(){
        ResponseResult result = new ResponseResult();
        List<CouponTemplate> templateList = this.couponTemplateService.findAll();
        return CollectionUtils.isEmpty(templateList) ? result.success("没有数据返回", null) :
                result.success("查询成功", templateList);
    }

    @RequestMapping(value = "/couponTemplate/getReceivedCouponNum",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定模板已经领取的优惠券数量")
    public ResponseResult getReceivedCouponNum(@RequestParam("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        Long receivedNum = this.couponTemplateService.getReceivedCouponNum(templateId);
        return receivedNum == null ? result.success("没有数据返回", null) :
                result.success("查询成功", receivedNum);
    }

    @RequestMapping(value = "/couponTemplate/getUsedCouponNum",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定模板已经使用的优惠券数量")
    public ResponseResult getUsedCouponNum(@RequestParam("templateId") Long templateId){
        ResponseResult result = new ResponseResult();
        Long receivedNum = this.couponTemplateService.getUsedCouponNum(templateId);
        return receivedNum == null ? result.success("没有数据返回", null) :
                result.success("查询成功", receivedNum);
    }

    @RequestMapping(value = "/couponTemplate/audit",method = RequestMethod.POST)
    @ApiOperation(value = "对优惠券模板进行审核")
    public ResponseResult auditTemplate(@RequestParam("templateId") Long templateId) {
        ResponseResult result = new ResponseResult();
        CouponTemplateVO couponTemplateVO = this.couponTemplateService.find(templateId);
        if (couponTemplateVO == null) {
            return result.failed("不存在这个优惠券模板");
        }

        CouponTemplateDTO couponTemplateDTO = BeanUtils.copy(couponTemplateVO,CouponTemplateDTO.class);
        couponTemplateDTO.setTemplateStatus(TemplateStatus.AUDITED.getCode());
        couponTemplateDTO.setUpdateTime(LocalDateTime.now());
        int code = this.couponTemplateService.update(couponTemplateDTO);
        return code == 0 ? result.failed("审核优惠券模板到审核状态失败") :
                result.success("更新优惠券模板到审核状态成功",null);
    }

    @RequestMapping(value = "/couponTemplate/reject",method = RequestMethod.POST)
    @ApiOperation(value = "拒绝当前优惠券模板")
    public ResponseResult rejectTemplate(@RequestParam("templateId") Long templateId) {
        ResponseResult result = new ResponseResult();
        CouponTemplateVO couponTemplateVO = this.couponTemplateService.find(templateId);
        if (couponTemplateVO == null) {
            return result.failed("没有这个模板");
        }

        CouponTemplateDTO couponTemplateDTO = BeanUtils.copy(couponTemplateVO,CouponTemplateDTO.class);
        couponTemplateDTO.setTemplateStatus(TemplateStatus.REJECTED.getCode());
        couponTemplateDTO.setUpdateTime(LocalDateTime.now());
        int code = this.couponTemplateService.update(couponTemplateDTO);
        return code == 0 ? result.failed("更新优惠券模板到拒绝状态失败") :
                result.success("更新优惠券模板到拒绝状态成功",null);
    }

    @RequestMapping(value = "/couponTemplate/launchCoupon",method = RequestMethod.POST)
    @ApiOperation(value = "批准优惠券，开始发放优惠券")
    public ResponseResult launchCoupon(@RequestParam("templateId") Long templateId) {
        ResponseResult result = new ResponseResult();

        int code = this.couponTemplateService.issueCoupon(templateId);
        if (code == 0) {
            return result.failed("发放优惠券失败");
        }
        return result.success("发放惠券模板成功",null);
    }


    @RequestMapping(value = "/couponTemplate/findBySkuId",method = RequestMethod.GET)
    @ApiOperation(value = "根据sku id查找促销活动")
    public ResponseResult<List<CouponTemplate>> findBySkuId(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        if (skuId == null) {
            return result.failed("sku id参数为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListBySkuId(skuId);
        return CollectionUtils.isEmpty(templateList)? result.success("这个sku id 没有优惠券模板",null) :
                result.success("查询成功",templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListBySkuIds",method = RequestMethod.GET)
    @ApiOperation(value = "根据sku id数组查找优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListBySkuIds(@RequestBody Long[] skuIds) {
        ResponseResult result = new ResponseResult();
        if (ArrayUtils.isEmpty(skuIds)) {
            return result.failed("sku id参数数组为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListBySkuIds(skuIds);
        return CollectionUtils.isEmpty(templateList)? result.success("这些sku id 没有优惠券模板",null) :
                result.success("查询成功",templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListByCatId",method = RequestMethod.GET)
    @ApiOperation(value = "根据cat id查找所有可用的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListByCatId(@RequestParam("catId") Long catId) {
        ResponseResult result = new ResponseResult();
        if (catId == null) {
            return result.failed("cat id参数为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListByCatId(catId);
        return CollectionUtils.isEmpty(templateList)? result.success("这个cat id 没有优惠券模板",null) :
                result.success("查询成功",templateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateListByBrandId",method = RequestMethod.GET)
    @ApiOperation(value = "根据cat id查找所有可用的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findTemplateListByBrandId(@RequestParam("brandId") Long brandId) {
        ResponseResult result = new ResponseResult();
        if (brandId == null) {
            return result.failed("brand id参数为空");
        }
        List<CouponTemplate> templateList = this.couponTemplateService.findTemplateListByBrandId(brandId);
        return CollectionUtils.isEmpty(templateList)? result.success("这个brand id 没有优惠券模板",null) :
                result.success("查询成功",templateList);
    }

    @RequestMapping(value = "/couponTemplate/findAllAvailableTemplateList",method = RequestMethod.GET)
    @ApiOperation(value = "根据商品查找所有可用的优惠券模板")
    public ResponseResult<List<CouponTemplate>> findAllAvailableTemplateList(@RequestParam("skuId") Long skuId) {
        ResponseResult result = new ResponseResult();
        if (skuId == null) {
            return result.failed("sku id参数为空");
        }

        List<CouponTemplate> availableTemplateList = new ArrayList<>();
        ResponseResult<SkuVo> findSKUResult = this.catalogService.findBySkuId(skuId);
        if (findSKUResult.getCode() != 200 || findSKUResult.getData() == null) {
            return result.failed("不能查询到这个SKU");
        }
        log.info(JSONObject.toJSONString(findSKUResult));
        SkuVo sku = JSONUtils.convert(JSONObject.toJSONString(findSKUResult.getData()), new TypeReference<SkuVo>() {
        });
        Product product = sku.getProduct();
        Long catId = product.getCategory().getId();
        Long brandId = product.getBrand().getId();

        List<CouponTemplate> skuCouponTemplateList = this.couponTemplateService.findTemplateListBySkuId(skuId);
        if (CollectionUtils.isNotEmpty(skuCouponTemplateList)) {
            availableTemplateList.addAll(skuCouponTemplateList);
        }

        List<CouponTemplate> catCouponTemplateList = this.couponTemplateService.findAvailableTemplateListByCatId(catId);
        if (CollectionUtils.isNotEmpty(catCouponTemplateList)) {
            availableTemplateList.addAll(catCouponTemplateList);
        }

        List<CouponTemplate> brandCouponTemplateList = this.couponTemplateService.findAvailableTemplateListByBrandId(brandId);
        if (CollectionUtils.isNotEmpty(brandCouponTemplateList)) {
            availableTemplateList.addAll(brandCouponTemplateList);
        }

        return CollectionUtils.isEmpty(availableTemplateList)? result.success("这个商品没有优惠券模板",null) :
                result.success("查询成功",availableTemplateList);
    }

    @RequestMapping(value = "/couponTemplate/findTemplateByCouponId",method = RequestMethod.GET)
    @ApiOperation(value = "根据优惠券查找对应的优惠券模板")
    public ResponseResult<CouponTemplateVO> findTemplateByCouponId(@RequestParam("couponId") Long couponId) {
        ResponseResult<CouponTemplateVO> result = new ResponseResult<CouponTemplateVO>();
        if (couponId == null) {
            return result.failed("优惠券id参数为空");
        }
        CouponTemplateVO couponTemplateVO = this.couponTemplateService.findTemplateByCouponId(couponId);
        return (couponTemplateVO == null) ? result.success("没有优惠券模板信息",null) :
            result.success("查询成功",couponTemplateVO);
    }
}
