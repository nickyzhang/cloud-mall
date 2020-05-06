package com.cloud.mapper;

import com.cloud.model.catalog.CategoryBrand;
import com.cloud.model.catalog.Sku;
import com.cloud.model.catalog.SkuProperties;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SkuPropertiesMapper {

    public List<SkuProperties> findBySkuId(Long skuId);

    public List<SkuProperties> findByAttributeValueId(Long attributeValueId);

    public int saveSkuProperties(SkuProperties skuProperties);

    public int deleteSkuProperties(Map<String,Object> map);

    public int deleteSkuPropertiesBySkuId(Long skuId);

    public int deleteSkuPropertiesByAttributeValueId(Long attributeValueId);

    public int batchDeleteSkuPropertiesBySkuList(List<Sku> skuList);
}
