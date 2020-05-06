package com.cloud.mapper;

import com.cloud.model.promotion.CouponTemplateSku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CouponTemplateSkuMapper {

    public List<CouponTemplateSku> findBySkuId(Long skuId);

    public List<CouponTemplateSku> findByTemplateId(Long templateId);

    public int saveCouponTemplateSku(CouponTemplateSku couponTemplateSku);

    public int deleteCouponTemplateSku(Map<String,Object> map);

    public int deleteCouponTemplateSkuBySkuId(Long skuId);

    public int deleteCouponTemplateSkuByTemplateId(Long templateId);
}
