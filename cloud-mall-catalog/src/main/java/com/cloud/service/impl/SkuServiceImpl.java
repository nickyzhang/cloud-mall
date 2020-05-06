package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.SkuDto;
import com.cloud.mapper.*;
import com.cloud.model.catalog.*;
import com.cloud.service.SkuService;
import com.cloud.vo.catalog.SkuVo;
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
public class SkuServiceImpl implements SkuService {

    private static Logger logger = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    AttributeValueMapper attributeValueMapper;

    @Autowired
    AttributeNameMapper attributeNameMapper;

    @Autowired
    BundleMapper bundleMapper;

    @Autowired
    SkuBundleMapper skuBundleMapper;

    @Autowired
    SkuPropertiesMapper skuPropertiesMapper;

    @Override
    public int save(SkuDto skuDto) {
        if (skuDto == null) {
            return 0;
        }
        Sku sku = BeanUtils.copy(skuDto,Sku.class);
        int code = this.skuMapper.save(sku);
        if (code == 0) {
            return code;
        }

        List<AttributeValue> attributeValueList = skuDto.getProperties();
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            SkuProperties skuProperties = null;
            for (AttributeValue attributeValue : attributeValueList) {
                skuProperties = new SkuProperties();
                skuProperties.setSkuId(sku.getId());
                skuProperties.setAttributeValueId(attributeValue.getId());
                LocalDateTime now = LocalDateTime.now();
                skuProperties.setCreateTime(now);
                skuProperties.setUpdateTime(now);
                code = this.skuPropertiesMapper.saveSkuProperties(skuProperties);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<Bundle> bundleList = skuDto.getBundleList();
        if (CollectionUtils.isNotEmpty(bundleList)) {
            SkuBundle skuBundle = null;
            for (Bundle bundle : bundleList) {
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
    public int saveList(List<Sku> skuList) {

        return this.skuMapper.saveList(skuList);
    }

    @Override
    public int update(SkuDto skuDto) {
        if (skuDto == null) {
            return 0;
        }
        Sku sku = BeanUtils.copy(skuDto,Sku.class);
        int code = this.skuMapper.update(sku);
        if (code == 0) {
            return code;
        }

        List<SkuProperties> skuPropertiesList = this.skuPropertiesMapper.
                findBySkuId(sku.getId());
        if (CollectionUtils.isNotEmpty(skuPropertiesList)) {
            Set<Long> oldPropertiesSet = new HashSet<>();
            for (SkuProperties skuProperties : skuPropertiesList) {
                oldPropertiesSet.add(skuProperties.getAttributeValueId());
            }

            Set<Long> newPropertiesSet = new HashSet<>();
            for (AttributeValue properties : skuDto.getProperties()) {
                newPropertiesSet.add(properties.getId());
            }

            Set<Long> deletePropertiesSet = Sets.difference(oldPropertiesSet,newPropertiesSet);
            for (Long deletePropertiesId : deletePropertiesSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("skuId",sku.getId());
                map.put("attributeValueId",deletePropertiesId);
                code = this.skuPropertiesMapper.deleteSkuProperties(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addPropertiesSet = Sets.difference(newPropertiesSet,oldPropertiesSet);
            for (Long addPropertiesId : addPropertiesSet) {
                SkuProperties skuProperties = new SkuProperties();
                skuProperties.setSkuId(sku.getId());
                skuProperties.setAttributeValueId(addPropertiesId);
                LocalDateTime now = LocalDateTime.now();
                skuProperties.setCreateTime(now);
                skuProperties.setUpdateTime(now);
                code = this.skuPropertiesMapper.saveSkuProperties(skuProperties);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<SkuBundle> skuBundleList = this.skuBundleMapper.
                findBySkuId(sku.getId());
        if (CollectionUtils.isNotEmpty(skuBundleList)) {
            Set<Long> oldBundleSet = new HashSet<>();
            for (SkuBundle skuBundle : skuBundleList) {
                oldBundleSet.add(skuBundle.getBundleId());
            }

            Set<Long> newBundleSet = new HashSet<>();
            for (Bundle bundle : skuDto.getBundleList()) {
                newBundleSet.add(bundle.getId());
            }

            Set<Long> deleteBundleSet = Sets.difference(oldBundleSet,newBundleSet);
            for (Long deleteBundleId : deleteBundleSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("skuId",sku.getId());
                map.put("bundleId",deleteBundleId);
                code = this.skuBundleMapper.deleteSkuBundle(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addBundleSet = Sets.difference(newBundleSet,oldBundleSet);
            for (Long addBundleId : addBundleSet) {
                SkuBundle skuBundle = new SkuBundle();
                skuBundle.setSkuId(sku.getId());
                skuBundle.setBundleId(addBundleId);
                LocalDateTime now = LocalDateTime.now();
                skuBundle.setCreateTime(now);
                skuBundle.setUpdateTime(now);
                code = this.skuBundleMapper.saveSkuBundle(skuBundle);
                if (code == 0) {
                    return code;
                }
            }
        }

        return 0;
    }

    @Override
    public int delete(Long id) {
        int code = this.skuMapper.delete(id);
        if (code == 0) {
            return code;
        }

        code = this.skuPropertiesMapper.deleteSkuPropertiesBySkuId(id);
        if (code == 0) {
            return code;
        }

        return this.skuBundleMapper.deleteSkuBundleBySkuId(id);
    }

    @Override
    public SkuVo find(Long id) {
        Sku sku = this.skuMapper.find(id);
        if (sku == null) {
            return null;
        }

        SkuDto skuDto = BeanUtils.copy(sku,SkuDto.class);

        List<AttributeValue> properties = this.attributeValueMapper.findAttributeValueListBySkuId(id);
        if (CollectionUtils.isNotEmpty(properties)) {
            skuDto.setProperties(properties);
        }

        List<Bundle> bundleList = this.bundleMapper.findBundleListBySkuId(id);
        if (CollectionUtils.isNotEmpty(bundleList)) {
            skuDto.setBundleList(bundleList);
        }
        return BeanUtils.copy(skuDto,SkuVo.class);
    }

    @Override
    public List<Sku> findAll() {
        return this.skuMapper.findAll();
    }

    @Override
    public Long count() {
        return this.skuMapper.count();
    }

    @Override
    public List<Sku> findSkuListByProductId(Long productId) {
        return this.skuMapper.findSkuListByProductId(productId);
    }

    @Override
    public List<Sku> findSkuListByBundleId(Long bundleId) {
        return this.skuMapper.findSkuListByBundleId(bundleId);
    }

    @Override
    public Map<String,Object> findProperties(Long skuId) {
        List<AttributeValue> attributeValueList = this.attributeValueMapper.findAttributeValueListBySkuId(skuId);
        if (CollectionUtils.isEmpty(attributeValueList)) {
            return null;
        }
        Map<String,Object> properties = new HashMap<>();
        for (AttributeValue attributeValue : attributeValueList) {
            AttributeName attributeName = this.attributeNameMapper.find(attributeValue.getName().getId());
            if (attributeName == null) {
                continue;
            }
            properties.put(attributeName.getName(),attributeValue.getValue());
        }
        return properties;
    }

    @Override
    public Map<String,Object> findSalesProperties(Long skuId) {
        List<AttributeValue> attributeValueList = this.attributeValueMapper.findAttributeValueListBySkuId(skuId);
        if (CollectionUtils.isEmpty(attributeValueList)) {
            return null;
        }
        Map<String,Object> properties = new HashMap<>();
        for (AttributeValue attributeValue : attributeValueList) {
            AttributeName attributeName = this.attributeNameMapper.find(attributeValue.getName().getId());
            if (attributeName == null) {
                continue;
            }
            if ("销售属性".equals(attributeName.getGroup())) {
                properties.put(attributeName.getName(),attributeValue.getValue());
            }
        }
        return properties;
    }
}
