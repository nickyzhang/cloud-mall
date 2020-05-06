package com.cloud.service;

import com.cloud.dto.catalog.CategoryDto;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    int save(CategoryDto category);

    int saveList(List<Category> categories);

    int update(CategoryDto category);

    int delete(Long catId);

    CategoryDto find(Long catId);

    List<Category> findAll();

    Long count();

    CategoryDto findByName(String name);

    List<Category> findCategoriesByBrandId(Long brandId);

    List<Category> findCategoriesByAttributeNameId(Long attributeNameId);
}
