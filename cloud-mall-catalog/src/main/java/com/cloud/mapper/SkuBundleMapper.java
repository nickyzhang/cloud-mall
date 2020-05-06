package com.cloud.mapper;

import com.cloud.model.catalog.Sku;
import com.cloud.model.catalog.SkuBundle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SkuBundleMapper {

    public List<SkuBundle> findBySkuId(Long skuId);

    public List<SkuBundle> findByBundleId(Long bundleId);

    public int saveSkuBundle(SkuBundle skuBundle);

    public int updateSkuBundle(SkuBundle skuBundle);

    public int deleteSkuBundle(Map<String,Object> map);

    public int deleteSkuBundleBySkuId(Long skuId);

    public int deleteSkuBundleByBundleId(Long bundleId);

    public int batchDeleteSkuBundleBySkuList(List<Sku> skuList);
}
