package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.BrandDto;
import com.cloud.mapper.BrandMapper;
import com.cloud.mapper.CategoryBrandMapper;
import com.cloud.mapper.CategoryMapper;
import com.cloud.mapper.ProductMapper;
import com.cloud.model.catalog.Brand;
import com.cloud.model.catalog.Category;
import com.cloud.model.catalog.CategoryBrand;
import com.cloud.model.catalog.Product;
import com.cloud.service.BrandService;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CategoryBrandMapper categoryBrandMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int save(BrandDto brandDto) {
        if (brandDto == null) {
            return 0;
        }
        Brand brand = BeanUtils.copy(brandDto,Brand.class);
        int code = this.brandMapper.save(brand);
        if (code == 0) {
            return 0;
        }

        List<Category> categoryList = brandDto.getCategoryList();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            CategoryBrand categoryBrand = null;
            for (Category category : categoryList) {
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

        return 0;
    }

    @Override
    public int saveList(List<Brand> brandList) {

        return this.brandMapper.saveList(brandList);
    }

    @Override
    public int update(BrandDto brandDto) {
        if (brandDto == null) {
            return 0;
        }
        Brand brand = BeanUtils.copy(brandDto,Brand.class);

        int code = this.brandMapper.update(brand);
        if (code == 0) {
            return 0;
        }

        List<CategoryBrand> categoryBrandList = this.categoryBrandMapper.findByBrandId(brand.getId());
        if (CollectionUtils.isNotEmpty(categoryBrandList)) {
            Set<Long> oldCategorySet = new HashSet<>();
            for (CategoryBrand categoryBrand : categoryBrandList) {
                oldCategorySet.add(categoryBrand.getCategoryId());
            }

            Set<Long> newCategorySet = new HashSet<>();
            for (Category category : brandDto.getCategoryList()) {
                newCategorySet.add(category.getId());
            }

            Set<Long> deleteCategorySet = Sets.difference(oldCategorySet,newCategorySet);
            for (Long deleteCategoryId : deleteCategorySet) {
                Map<String,Object> map = new HashMap<>();
                map.put("catId",deleteCategoryId);
                map.put("brandId",brand.getId());
                code = this.categoryBrandMapper.deleteCategoryBrand(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addCategorySet = Sets.difference(newCategorySet,oldCategorySet);
            for (Long addCategoryId : addCategorySet) {
                CategoryBrand categoryBrand = new CategoryBrand();
                categoryBrand.setCategoryId(addCategoryId);
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
        return code;
    }

    @Override
    public int delete(Long id) {
        // 如果待删除的品牌还有产品存在，需要先删除或者解除产品和品牌的关系，才能删除
        List<Product> productList = this.productMapper.findProductListByBrandId(id);
        if (CollectionUtils.isNotEmpty(productList)) {
            return 0;
        }

        int code = this.brandMapper.delete(id);
        if (code == 0) {
            return code;
        }

        return this.categoryBrandMapper.deleteCategoryBrandByBrandId(id);
    }

    @Override
    public BrandDto find(Long id) {
        Brand brand = this.brandMapper.find(id);
        if (brand == null) {
            return null;
        }

        BrandDto brandDto = BeanUtils.copy(brand, BrandDto.class);

        List<Category> categoryList = this.categoryMapper.findCategoriesByBrandId(id);
        brandDto.setCategoryList(categoryList);

        return brandDto;
    }

    @Override
    public List<Brand> findAll() {
        return this.brandMapper.findAll();
    }

    @Override
    public Long count() {
        return this.brandMapper.count();
    }

    @Override
    public BrandDto findByName(String name) {
        Brand brand = this.brandMapper.findByName(name);
        if (brand == null) {
            return null;
        }

        BrandDto brandDto = BeanUtils.copy(brand, BrandDto.class);

        List<Category> categoryList = this.categoryMapper.findCategoriesByBrandId(brand.getId());
        brandDto.setCategoryList(categoryList);

        return brandDto;
    }

    @Override
    public List<Brand> findBrandsByCategoryId(Long categoryId) {

        return this.brandMapper.findBrandsByCategoryId(categoryId);
    }
}
