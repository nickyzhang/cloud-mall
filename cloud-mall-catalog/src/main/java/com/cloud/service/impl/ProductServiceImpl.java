package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.AttributeValueDto;
import com.cloud.dto.catalog.ProductDto;
import com.cloud.mapper.*;
import com.cloud.model.catalog.*;
import com.cloud.service.ProductService;
import com.cloud.service.SkuService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    AttributeValueMapper attributeValueMapper;

    @Autowired
    AttributeNameMapper attributeNameMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuPropertiesMapper skuPropertiesMapper;

    @Autowired
    SkuBundleMapper skuBundleMapper;

    @Autowired
    ProductPropertiesMapper productPropertiesMapper;

    @Override
    public int save(ProductDto productDto) {
        if (productDto == null) {
            return 0;
        }
        Product product = BeanUtils.copy(productDto,Product.class);
        int code = this.productMapper.save(product);
        if (code == 0) {
            return code;
        }

        List<AttributeValue> attributeValueList = productDto.getProperties();
        if (CollectionUtils.isNotEmpty(attributeValueList)) {
            ProductProperties productProperties = null;
            for (AttributeValue attributeValue : attributeValueList) {
                productProperties = new ProductProperties();
                productProperties.setProductId(product.getId());
                productProperties.setAttributeValueId(attributeValue.getId());
                LocalDateTime now = LocalDateTime.now();
                productProperties.setCreateTime(now);
                productProperties.setUpdateTime(now);
                code = this.productPropertiesMapper.saveProductProperties(productProperties);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<Sku> skuList = productDto.getSkuList();
        if (CollectionUtils.isNotEmpty(skuList)) {
            code = this.skuMapper.saveList(skuList);
            if (code == 0) {
                return code;
            }
        }

        return code;
    }

    @Override
    public int saveList(List<Product> productList) {

        return this.productMapper.saveList(productList);
    }

    @Override
    public int update(ProductDto productDto) {
        if (productDto == null) {
            return 0;
        }
        Product product = BeanUtils.copy(productDto,Product.class);
        int code = this.productMapper.update(product);
        if (code == 0) {
            return code;
        }

        List<ProductProperties> productPropertiesList = this.productPropertiesMapper.
                findByProductId(product.getId());
        if (CollectionUtils.isNotEmpty(productPropertiesList)) {
            Set<Long> oldPropertiesSet = new HashSet<>();
            for (ProductProperties properties : productPropertiesList) {
                oldPropertiesSet.add(properties.getAttributeValueId());
            }

            Set<Long> newPropertiesSet = new HashSet<>();
            for (AttributeValue properties : productDto.getProperties()) {
                newPropertiesSet.add(properties.getId());
            }

            Set<Long> deletePropertiesSet = Sets.difference(oldPropertiesSet,newPropertiesSet);
            for (Long deletePropertiesId : deletePropertiesSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("productId",product.getId());
                map.put("attributeValueId",deletePropertiesId);
                code = this.productPropertiesMapper.deleteProductProperties(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addPropertiesSet = Sets.difference(newPropertiesSet,oldPropertiesSet);
            for (Long addPropertiesId : addPropertiesSet) {
                ProductProperties productProperties = new ProductProperties();
                productProperties.setProductId(product.getId());
                productProperties.setAttributeValueId(addPropertiesId);
                LocalDateTime now = LocalDateTime.now();
                productProperties.setCreateTime(now);
                productProperties.setUpdateTime(now);
                code = this.productPropertiesMapper.saveProductProperties(productProperties);
                if (code == 0) {
                    return code;
                }
            }
        }
        return code;
    }

    @Override
    public int delete(Long id) {
        // 删除产品下所有的sku，并且解除绑定关系
        List<Sku> skuList = this.skuMapper.findSkuListByProductId(id);
        int code = 0;
        if (CollectionUtils.isNotEmpty(skuList)) {
            code = this.skuPropertiesMapper.batchDeleteSkuPropertiesBySkuList(skuList);
            if (code == 0) {
                return 0;
            }
            code = this.skuBundleMapper.batchDeleteSkuBundleBySkuList(skuList);
            if (code == 0) {
                return 0;
            }
            code = this.skuMapper.batchDelete(skuList);
            if (code == 0) {
                return 0;
            }
        }

        // 删除产品
        code = this.productMapper.delete(id);
        if (code == 0) {
            return 0;
        }
        // 解除产品和属性值的关系
        return this.productPropertiesMapper.deleteProductPropertiesByProductId(id);
    }

    @Override
    public ProductDto find(Long id) {
        Product product = this.productMapper.find(id);
        if (product == null) {
            return null;
        }

        ProductDto productDto = BeanUtils.copy(product, ProductDto.class);

        List<AttributeValue> properties = this.attributeValueMapper.findAttributeValueListByProductId(product.getId());
        if (CollectionUtils.isNotEmpty(properties)) {
            productDto.setProperties(properties);
        }

        List<Sku> skuList = this.skuMapper.findSkuListByProductId(product.getId());
        if (CollectionUtils.isNotEmpty(skuList)) {
            productDto.setSkuList(skuList);
        }

        return productDto;
    }

    @Override
    public List<Product> findAll() {

        return this.productMapper.findAll();
    }

    @Override
    public Long count() {

        return this.productMapper.count();
    }

    @Override
    public List<Product> findProductListByCategoryId(Long categoryId) {

        return this.productMapper.findProductListByCategoryId(categoryId);
    }

    @Override
    public List<Product> findProductListByBrandId(Long brandId) {

        return this.productMapper.findProductListByBrandId(brandId);
    }

    @Override
    public Map<String,Object> findProperties(Long productId) {
        List<AttributeValue> attributeValueList = this.attributeValueMapper.findAttributeValueListByProductId(productId);
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
}
