package com.cloud.service;

import com.cloud.common.core.bean.ResponseResult;
import com.cloud.dto.promotion.CouponTemplateDTO;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.vo.promotion.CouponTemplateVO;

import java.util.List;
import java.util.Map;

public interface CouponTemplateService {

    public int save(CouponTemplateDTO templateDTO);

    public int saveList(List<CouponTemplate> templateList);

    public int update(CouponTemplateDTO templateDTO);

    public int delete(Long templateId);

    public int deleteAllExpiredTemplateList();

    public CouponTemplateVO find(Long templateId);

    public List<CouponTemplate> findTemplateListBySkuId(Long skuId);

    public List<CouponTemplate> findTemplateListByCatId(Long catId);

    public List<CouponTemplate> findTemplateListByBrandId(Long brandId);

    public List<CouponTemplate> findAvailableTemplateListBySkuId(Long skuId);

    public List<CouponTemplate> findAvailableTemplateListByCatId(Long catId);

    public List<CouponTemplate> findAvailableTemplateListByBrandId(Long brandId);

    public List<CouponTemplate> findTemplateListCouponType(Long couponType);

    public List<CouponTemplate> findTemplateListPromotionMethod(Long promotionMethod);

    public List<CouponTemplate> findTemplateListAfterIssueTime(String issueTime);

    public List<CouponTemplate> findTemplateListBeforeIssueTime(String issueTime);

    public List<CouponTemplate> findTemplateListBetweenIssueTime(String startTime, String endTime);

    public List<CouponTemplate> findExpiredTemplateList();

    public List<CouponTemplate> findAll();

    public Long getReceivedCouponNum(Long templateId);

    public Long getUsedCouponNum(Long templateId);

    public int issueCoupon(Long templateId);

    public List<CouponTemplate> findTemplateListBySkuIds(Long[] skuIds);

    public CouponTemplateVO findTemplateByCouponId(Long couponId);
}
