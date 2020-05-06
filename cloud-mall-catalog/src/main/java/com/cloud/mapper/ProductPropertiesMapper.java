package com.cloud.mapper;

import com.cloud.model.catalog.ProductProperties;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductPropertiesMapper {

    public List<ProductProperties> findByProductId(Long productId);

    public List<ProductProperties> findByAttributeValueId(Long attributeValueId);

    public int saveProductProperties(ProductProperties productProperties);

    public int deleteProductProperties(Map<String,Object> map);

    public int deleteProductPropertiesByProductId(Long productId);

    public int deleteProductPropertiesByByAttributeValueId(Long attributeValueId);

}
