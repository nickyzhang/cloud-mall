package com.cloud.mapper;

import com.cloud.model.catalog.CategoryAttributeName;
import com.cloud.model.catalog.CategoryAttributeName;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryAttributeNameMapper {

    public List<CategoryAttributeName> findByCatId(Long catId);

    public List<CategoryAttributeName> findByAttributeNameId(Long attributeNameId);

    public int saveCategoryAttributeName(CategoryAttributeName categoryAttributeName);

    public int deleteCategoryAttributeName(Map<String,Object> map);

    public int deleteCategoryAttributeNameByCatId(Long catId);

    public int deleteCategoryAttributeNameByAttributeNameId(Long attributeNameId);
}
