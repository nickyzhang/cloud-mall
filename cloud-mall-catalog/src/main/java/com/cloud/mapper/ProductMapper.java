package com.cloud.mapper;

import com.cloud.model.catalog.AttributeValue;
import com.cloud.model.catalog.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface ProductMapper {

    int save(Product model);

    int saveList(List<Product> modelList);

    int update(Product model);

    int delete(Long id);

    Product find(Long id);

    List<Product> findAll();

    Long count();

    int delete(Long[] ids);

    List<Product> findProductListByCategoryId(Long categoryId);

    List<Product> findProductListByBrandId(Long brandId);
}
