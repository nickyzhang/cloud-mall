package com.cloud.service;

import com.cloud.dto.catalog.ProductDto;
import com.cloud.model.catalog.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {

    int save(ProductDto productDto);

    int saveList(List<Product> productDtoList);

    int update(ProductDto productDto);

    int delete(Long id);

    ProductDto find(Long id);

    List<Product> findAll();

    Long count();

    List<Product> findProductListByCategoryId(Long categoryId);

    List<Product> findProductListByBrandId(Long brandId);

    Map<String,Object> findProperties(Long productId);
}
