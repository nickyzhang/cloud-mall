package com.cloud.service;

import com.cloud.dto.catalog.BundleDto;
import com.cloud.model.catalog.Bundle;
import java.util.List;

public interface BundleService {

    int save(BundleDto bundle);

    int saveList(List<Bundle> modelList);

    int update(BundleDto model);

    int delete(Long id);

    BundleDto find(Long id);

    List<Bundle> findAll();

    Long count();
}
