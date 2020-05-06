package com.cloud.service;

import com.cloud.dto.catalog.AttributeValueDto;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.AttributeValue;
import java.util.List;

public interface AttributeValueService {

    int save(AttributeValueDto attributeValue);

    int saveList(List<AttributeValue> attributeValueList);

    int update(AttributeValueDto model);

    int delete(Long id);

    AttributeValueDto find(Long id);

    List<AttributeValue> findAll();

    Long count();

    List<AttributeValue> findAttributeValueListByAttributeNameId(Long attributeNameId);

    List<AttributeValue> findAttributeValueListByProductId(Long productId);

    List<AttributeValue> findAttributeValueListBySkuId(Long skuId);
}
