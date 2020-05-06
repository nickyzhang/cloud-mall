package com.cloud.service;

import com.cloud.dto.catalog.BrandDto;
import com.cloud.model.catalog.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    int save(BrandDto model);

    int saveList(List<Brand> modelList);

    int update(BrandDto model);

    int delete(Long id);

    BrandDto find(Long id);

    List<Brand> findAll();

    Long count();

    BrandDto findByName(String name);

    List<Brand> findBrandsByCategoryId(Long categoryId);

}
