package com.cloud.mapper;

import com.cloud.model.catalog.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface CategoryMapper {

    int save(Category model);

    int saveList(List<Category> modelList);

    int update(Category model);

    int delete(Long id);

    Category find(Long id);

    Category findByName(String name);

    List<Category> findAll();

    Long count();

    List<Category> findCategoriesByBrandId(Long brandId);

    List<Category> findCategoriesByAttributeNameId(Long attributeNameId);


}
