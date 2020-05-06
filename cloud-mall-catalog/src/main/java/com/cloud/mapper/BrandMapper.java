package com.cloud.mapper;

import com.cloud.model.catalog.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BrandMapper {

    int save(Brand model);

    int saveList(List<Brand> modelList);

    int update(Brand model);

    int delete(Long id);

    Brand find(Long id);

    List<Brand> findAll();

    Long count();

    Brand findByName(String name);

    List<Brand> findBrandsByCategoryId(Long categoryId);
}
