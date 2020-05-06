package com.cloud.mapper;

import com.cloud.model.catalog.Sku;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SkuMapper {

    int save(Sku sku);

    int saveList(List<Sku> skuList);

    int update(Sku sku);

    int delete(Long skuId);

    int batchDelete(List<Sku> skuList);

    Sku find(Long skuId);

    List<Sku> findAll();

    Long count();

    List<Sku> findSkuListByProductId(Long productId);

    List<Sku> findSkuListByBundleId(Long bundleId);
}
