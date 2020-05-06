package com.cloud.service;

import com.cloud.dto.catalog.SkuDto;
import com.cloud.model.catalog.Sku;
import com.cloud.vo.catalog.SkuVo;

import java.util.List;
import java.util.Map;

public interface SkuService {

    int save(SkuDto skuDto);

    int saveList(List<Sku> skuList);

    int update(SkuDto skuDto);

    int delete(Long id);

    SkuVo find(Long id);

    List<Sku> findAll();

    Long count();

    List<Sku> findSkuListByProductId(Long productId);

    List<Sku> findSkuListByBundleId(Long bundleId);

    Map<String,Object> findProperties(Long skuId);

    Map<String,Object> findSalesProperties(Long skuId);
}
