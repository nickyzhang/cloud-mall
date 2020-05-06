package com.cloud.mapper;

import com.cloud.model.catalog.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryBrandMapper {

    public List<CategoryBrand> findByCatId(Long catId);

    public List<CategoryBrand> findByBrandId(Long brandId);

    public int saveCategoryBrand(CategoryBrand categoryBrand);

    public int updateCategoryBrand(Map<String,Object> map);

    public int deleteCategoryBrand(Map<String,Object> map);

    public int deleteCategoryBrandByCatId(Long catId);

    public int deleteCategoryBrandByBrandId(Long brandId);
}
