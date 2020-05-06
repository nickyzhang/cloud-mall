package com.cloud.mapper;

import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CouponTemplateMapper {

    public int save(CouponTemplate template);

    public int saveList(List<CouponTemplate> templateList);

    public int update(CouponTemplate template);

    public int delete(Long templateId);

    public int deleteAllExpiredTemplateList();

    public CouponTemplate find(Long templateId);

    public List<CouponTemplate> findTemplateListBySkuId(Long skuId);

    public List<CouponTemplate> findTemplateListByCatId(Long catId);

    public List<CouponTemplate> findTemplateListByBrandId(Long brandId);

    public List<CouponTemplate> findAvailableTemplateListBySkuId(Long skuId);

    public List<CouponTemplate> findAvailableTemplateListByCatId(Long catId);

    public List<CouponTemplate> findAvailableTemplateListByBrandId(Long brandId);

    public List<CouponTemplate> findTemplateListBySkuIds(Long[] skuIds);

    public List<CouponTemplate> findTemplateListCouponType(Long couponType);

    public List<CouponTemplate> findTemplateListPromotionMethod(Long promotionMethod);

    public List<CouponTemplate> findTemplateListAfterIssueTime(String issueTime);

    public List<CouponTemplate> findTemplateListBeforeIssueTime(String issueTime);

    public List<CouponTemplate> findTemplateListBetweenIssueTime(Map<String,Object> map);

    public List<CouponTemplate> findExpiredTemplateList();

    public List<CouponTemplate> findAll();

    public Long getReceivedCouponNum(Long templateId);

    public Long getUsedCouponNum(Long templateId);

    public CouponTemplate findTemplateByCouponId(Long couponId);
}
