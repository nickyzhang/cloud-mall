package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.BundleDto;
import com.cloud.mapper.BundleMapper;
import com.cloud.mapper.SkuBundleMapper;
import com.cloud.mapper.SkuMapper;
import com.cloud.model.catalog.Bundle;
import com.cloud.model.catalog.Sku;
import com.cloud.model.catalog.SkuBundle;
import com.cloud.service.BundleService;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class BundleServiceImpl implements BundleService {

    private static Logger logger = LoggerFactory.getLogger(BundleServiceImpl.class);

    @Autowired
    BundleMapper bundleMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuBundleMapper skuBundleMapper;


    @Override
    public int save(BundleDto bundleDto) {
        if (bundleDto == null) {
            return 0;
        }
        Bundle bundle = BeanUtils.copy(bundleDto,Bundle.class);
        int code = this.bundleMapper.save(bundle);
        if (code == 0) {
            return code;
        }

        List<Sku> skuList = bundleDto.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            SkuBundle skuBundle = null;
            for (Sku sku : skuList) {
                skuBundle = new SkuBundle();
                skuBundle.setSkuId(sku.getId());
                skuBundle.setBundleId(bundle.getId());
                LocalDateTime now = LocalDateTime.now();
                skuBundle.setCreateTime(now);
                skuBundle.setUpdateTime(now);
                code = this.skuBundleMapper.saveSkuBundle(skuBundle);
                if (code == 0) {
                    return code;
                }
            }
        }
        return code;
    }

    @Override
    public int saveList(List<Bundle> bundleList) {

        return this.bundleMapper.saveList(bundleList);
    }

    @Override
    public int update(BundleDto bundleDto) {
        if (bundleDto == null) {
            return 0;
        }
        Bundle bundle = BeanUtils.copy(bundleDto,Bundle.class);
        int code = this.bundleMapper.update(bundle);
        if (code == 0) {
            return code;
        }

        List<SkuBundle> skuBundleList = this.skuBundleMapper.
                findByBundleId(bundle.getId());
        if (CollectionUtils.isNotEmpty(skuBundleList)) {
            Set<Long> oldSkuSet = new HashSet<>();
            for (SkuBundle skuBundle : skuBundleList) {
                oldSkuSet.add(skuBundle.getSkuId());
            }

            Set<Long> newSkuSet = new HashSet<>();
            for (Sku sku : bundleDto.getSkuList()) {
                newSkuSet.add(sku.getId());
            }

            Set<Long> deleteSkuSet = Sets.difference(oldSkuSet,newSkuSet);
            for (Long deleteSkuId : deleteSkuSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("skuId",deleteSkuId);
                map.put("bundleId", bundle.getId());
                code = this.skuBundleMapper.deleteSkuBundle(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addSkuSet = Sets.difference(newSkuSet,oldSkuSet);
            for (Long addSkuId : addSkuSet) {
                SkuBundle skuBundle = new SkuBundle();
                skuBundle.setSkuId(addSkuId);
                skuBundle.setBundleId(bundle.getId());
                LocalDateTime now = LocalDateTime.now();
                skuBundle.setCreateTime(now);
                skuBundle.setUpdateTime(now);
                code = this.skuBundleMapper.saveSkuBundle(skuBundle);
                if (code == 0) {
                    return code;
                }
            }
        }
        return code;

    }

    @Override
    public int delete(Long id) {
        int code = this.bundleMapper.delete(id);
        if (code == 0) {
            return code;
        }

        return this.skuBundleMapper.deleteSkuBundleByBundleId(id);
    }

    @Override
    public BundleDto find(Long id) {
        Bundle bundle = this.bundleMapper.find(id);
        if (bundle == null) {
            return null;
        }

        BundleDto bundleDto = BeanUtils.copy(bundle,BundleDto.class);

        List<Sku> skuList = this.skuMapper.findSkuListByBundleId(bundle.getId());
        if (CollectionUtils.isNotEmpty(skuList)) {
            bundleDto.setSkuList(skuList);
        }
        return bundleDto;
    }

    @Override
    public List<Bundle> findAll() {
        return this.bundleMapper.findAll();
    }

    @Override
    public Long count() {
        return this.bundleMapper.count();
    }
}
