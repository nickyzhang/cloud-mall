package com.cloud.service;

import com.cloud.dto.catalog.AttributeNameDto;
import com.cloud.model.catalog.AttributeName;
import java.util.List;

public interface AttributeNameService {

    int save(AttributeNameDto attributeName);

    int saveList(List<AttributeName> attributeNameList);

    int update(AttributeNameDto attributeName);

    int delete(Long id);

    AttributeNameDto find(Long id);

    List<AttributeName> findAll();

    Long count();

    List<AttributeName> findAttributeNamesByCategoryId(Long categoryId);

}
