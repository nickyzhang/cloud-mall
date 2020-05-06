package com.cloud.mapper;

import com.cloud.model.catalog.Bundle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface BundleMapper {

    int save(Bundle model);

    int saveList(List<Bundle> modelList);

    int update(Bundle model);

    int delete(Long id);

    Bundle find(Long id);

    List<Bundle> findAll();

    Long count();

    List<Bundle> findBundleListBySkuId(Long skuId);

}
