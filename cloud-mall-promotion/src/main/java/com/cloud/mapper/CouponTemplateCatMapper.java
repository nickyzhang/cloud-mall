package com.cloud.mapper;

import com.cloud.model.promotion.CouponTemplateCat;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CouponTemplateCatMapper {

    public List<CouponTemplateCat> findByCatId(Long catId);

    public List<CouponTemplateCat> findByTemplateId(Long templateId);

    public int saveCouponTemplateCat(CouponTemplateCat couponTemplateCat);

    public int deleteCouponTemplateCat(Map<String,Object> map);

    public int deleteCouponTemplateCatByCatId(Long catId);

    public int deleteCouponTemplateCatByTemplateId(Long templateId);

}
