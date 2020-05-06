package com.cloud.mapper;

import com.cloud.model.catalog.AttributeValue;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
public interface AttributeValueMapper {

    int save(AttributeValue model);

    int saveList(List<AttributeValue> modelList);

    int update(AttributeValue model);

    int delete(Long id);

    AttributeValue find(Long id);

    List<AttributeValue> findAll();

    List<AttributeValue> findAttributeValueListByAttributeNameId(Long attributeNameId);
    
    List<AttributeValue> findAttributeValueListByProductId(Long productId);

    List<AttributeValue> findAttributeValueListBySkuId(Long skuId);

    Long count();
}
