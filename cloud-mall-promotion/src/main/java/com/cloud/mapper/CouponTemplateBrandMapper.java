package com.cloud.mapper;

import com.cloud.model.promotion.CouponTemplateBrand;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CouponTemplateBrandMapper {

    public List<CouponTemplateBrand> findByBrandId(Long brandId);

    public List<CouponTemplateBrand> findByTemplateId(Long templateId);

    public int saveCouponTemplateBrand(CouponTemplateBrand couponTemplateBrand);

    public int deleteCouponTemplateBrand(Map<String, Object> map);

    public int deleteCouponTemplateBrandByBrandId(Long brandId);

    public int deleteCouponTemplateBrandByTemplateId(Long templateId);

}
