package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.CategoryDto;
import com.cloud.mapper.*;
import com.cloud.model.catalog.*;
import com.cloud.service.CategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    AttributeNameMapper attributeNameMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryBrandMapper categoryBrandMapper;

    @Autowired
    CategoryAttributeNameMapper categoryAttributeNameMapper;

    @Override
    public int save(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return 0;
        }
        Category category = BeanUtils.copy(categoryDto,Category.class);
        int code = this.categoryMapper.save(category);
        if (code == 0) {
            return code;
        }

        List<Brand> brandList = categoryDto.getBrandList();
        if (CollectionUtils.isNotEmpty(brandList)) {
            CategoryBrand categoryBrand = null;
            for (Brand brand : brandList) {
                categoryBrand = new CategoryBrand();
                categoryBrand.setCategoryId(category.getId());
                categoryBrand.setBrandId(brand.getId());
                LocalDateTime now = LocalDateTime.now();
                categoryBrand.setCreateTime(now);
                categoryBrand.setUpdateTime(now);
                code = this.categoryBrandMapper.saveCategoryBrand(categoryBrand);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<AttributeName> attributeNameList = categoryDto.getAttributeNameList();
        if (CollectionUtils.isNotEmpty(attributeNameList)) {
            CategoryAttributeName categoryAttributeName = null;
            for (AttributeName attributeName : attributeNameList) {
                categoryAttributeName = new CategoryAttributeName();
                categoryAttributeName.setCategoryId(category.getId());
                categoryAttributeName.setAttributeNameId(attributeName.getId());
                LocalDateTime now = LocalDateTime.now();
                categoryAttributeName.setCreateTime(now);
                categoryAttributeName.setUpdateTime(now);
                code = this.categoryAttributeNameMapper.saveCategoryAttributeName(categoryAttributeName);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<Product> productList = categoryDto.getProductList();
        code = this.productMapper.saveList(productList);
        if (code == 0) {
            return code;
        }
        return code;
    }

    @Override
    public int saveList(List<Category> categories) {
        return this.categoryMapper.saveList(categories);
    }

    @Override
    public int update(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return 0;
        }
        Category category = BeanUtils.copy(categoryDto,Category.class);
        int code = this.categoryMapper.update(category);
        if (code == 0) {
            return code;
        }
        List<CategoryBrand> categoryBrandList = this.categoryBrandMapper.findByCatId(category.getId());
        if (CollectionUtils.isNotEmpty(categoryBrandList)) {
            Set<Long> oldBrandSet = new HashSet<>();
            for (CategoryBrand categoryBrand : categoryBrandList) {
                oldBrandSet.add(categoryBrand.getBrandId());
            }

            Set<Long> newBrandSet = new HashSet<>();
            for (Brand brand : categoryDto.getBrandList()) {
                newBrandSet.add(brand.getId());
            }

            Set<Long> deleteBrandSet = Sets.difference(oldBrandSet,newBrandSet);
            for (Long deleteBrandId : deleteBrandSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("catId",category.getId());
                map.put("brandId",deleteBrandId);
                code = this.categoryBrandMapper.deleteCategoryBrand(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addBrandSet = Sets.difference(newBrandSet,oldBrandSet);
            for (Long addBrandId : addBrandSet) {
                CategoryBrand categoryBrand = new CategoryBrand();
                categoryBrand.setCategoryId(category.getId());
                categoryBrand.setBrandId(addBrandId);
                LocalDateTime now = LocalDateTime.now();
                categoryBrand.setCreateTime(now);
                categoryBrand.setUpdateTime(now);
                code = this.categoryBrandMapper.saveCategoryBrand(categoryBrand);
                if (code == 0) {
                    return code;
                }
            }
        }

        List<CategoryAttributeName> categoryAttributeNameList = this.categoryAttributeNameMapper.findByCatId(category.getId());
        if (CollectionUtils.isNotEmpty(categoryAttributeNameList)) {
            Set<Long> oldAttributeNameSet = new HashSet<>();
            for (CategoryAttributeName categoryAttributeName : categoryAttributeNameList) {
                oldAttributeNameSet.add(categoryAttributeName.getAttributeNameId());
            }

            Set<Long> newAttributeNameSet = new HashSet<>();
            for (AttributeName attributeName : categoryDto.getAttributeNameList()) {
                newAttributeNameSet.add(attributeName.getId());
            }

            Set<Long> deletedAttributeNameSet = Sets.difference(oldAttributeNameSet,newAttributeNameSet);
            for (Long deleteAttributeNamed : deletedAttributeNameSet) {
                Map<String,Object> map = new HashMap<>();
                map.put("catId",category.getId());
                map.put("attributeNameId",deleteAttributeNamed);
                code = this.categoryAttributeNameMapper.deleteCategoryAttributeName(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addAttributeNameSet = Sets.difference(newAttributeNameSet,oldAttributeNameSet);
            for (Long attributeNameId : addAttributeNameSet) {
                CategoryAttributeName categoryAttributeName = new CategoryAttributeName();
                categoryAttributeName.setCategoryId(category.getId());
                categoryAttributeName.setAttributeNameId(attributeNameId);
                LocalDateTime now = LocalDateTime.now();
                categoryAttributeName.setCreateTime(now);
                categoryAttributeName.setUpdateTime(now);
                code = this.categoryAttributeNameMapper.saveCategoryAttributeName(categoryAttributeName);
                if (code == 0) {
                    return code;
                }
            }
        }
        return code;
    }

    @Override
    public int delete(Long catId) {
        List<Product> productList = this.productMapper.findProductListByCategoryId(catId);
        if (CollectionUtils.isNotEmpty(productList)) {
            return 0;
        }

        int code = this.categoryMapper.delete(catId);
        if (code == 0) {
            return code;
        }

        code = this.categoryBrandMapper.deleteCategoryBrandByCatId(catId);
        if (code == 0) {
            return code;
        }

        return this.categoryAttributeNameMapper.deleteCategoryAttributeNameByCatId(catId);
    }

    @Override
    public CategoryDto find(Long catId) {
        Category category = this.categoryMapper.find(catId);
        if (category == null) {
            return null;
        }

        CategoryDto categoryDto = BeanUtils.copy(category,CategoryDto.class);
        List<Brand> brandList = this.brandMapper.findBrandsByCategoryId(catId);
        if (CollectionUtils.isNotEmpty(brandList)) {
            categoryDto.setBrandList(brandList);
        }
        List<AttributeName> attributeNameList = this.attributeNameMapper.findAttributeNameListByCategoryId(catId);
        if (CollectionUtils.isNotEmpty(attributeNameList)) {
            categoryDto.setAttributeNameList(attributeNameList);
        }

        List<Product> productList = this.productMapper.findProductListByCategoryId(catId);
        if (CollectionUtils.isNotEmpty(productList)) {
            categoryDto.setProductList(productList);
        }
        return categoryDto;
    }

    @Override
    public List<Category> findAll() {

        return this.categoryMapper.findAll();
    }

    @Override
    public Long count() {
        return this.categoryMapper.count();
    }

    @Override
    public CategoryDto findByName(String name) {
        Category category = this.categoryMapper.findByName(name);
        if (category == null) {
            return null;
        }
        CategoryDto categoryDto = BeanUtils.copy(category,CategoryDto.class);
        List<Brand> brandList = this.brandMapper.findBrandsByCategoryId(category.getId());
        if (CollectionUtils.isNotEmpty(brandList)) {
            categoryDto.setBrandList(brandList);
        }
        List<AttributeName> attributeNameList = this.attributeNameMapper.
                findAttributeNameListByCategoryId(category.getId());
        if (CollectionUtils.isNotEmpty(attributeNameList)) {
            categoryDto.setAttributeNameList(attributeNameList);
        }

        List<Product> productList = this.productMapper.
                findProductListByCategoryId(category.getId());
        if (CollectionUtils.isNotEmpty(productList)) {
            categoryDto.setProductList(productList);
        }
        return categoryDto;
    }

    @Override
    public List<Category> findCategoriesByBrandId(Long brandId) {

        return this.categoryMapper.findCategoriesByBrandId(brandId);
    }

    @Override
    public List<Category> findCategoriesByAttributeNameId(Long attributeNameId) {

        return this.categoryMapper.findCategoriesByAttributeNameId(attributeNameId);
    }
}
