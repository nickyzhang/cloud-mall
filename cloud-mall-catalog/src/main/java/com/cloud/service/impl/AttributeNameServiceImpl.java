package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.AttributeNameDto;
import com.cloud.mapper.AttributeNameMapper;
import com.cloud.mapper.CategoryAttributeNameMapper;
import com.cloud.mapper.CategoryMapper;
import com.cloud.model.catalog.*;
import com.cloud.service.AttributeNameService;
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
public class AttributeNameServiceImpl implements AttributeNameService{

    private static Logger logger = LoggerFactory.getLogger(AttributeNameServiceImpl.class);

    @Autowired
    AttributeNameMapper attributeNameMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryAttributeNameMapper categoryAttributeNameMapper;
    
    @Override
    public int save(AttributeNameDto attributeNameDto) {
        if (attributeNameDto == null) {
            return 0;
        }
        AttributeName attributeName = BeanUtils.copy(attributeNameDto,AttributeName.class);
        int code = this.attributeNameMapper.save(attributeName);
        if (code == 0) {
            return 0;
        }

        List<Category> categoryList = attributeNameDto.getCategoryList();
        if (CollectionUtils.isNotEmpty(categoryList)) {
            CategoryAttributeName categoryAttributeName = null;
            for (Category category : categoryList) {
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

        return 0;
    }

    @Override
    public int saveList(List<AttributeName> attributeNameList) {
        return  this.attributeNameMapper.saveList(attributeNameList);
    }

    @Override
    public int update(AttributeNameDto attributeNameDto) {
        if (attributeNameDto == null) {
            return 0;
        }
        AttributeName attributeName = BeanUtils.copy(attributeNameDto,AttributeName.class);
        int code = this.attributeNameMapper.update(attributeName);
        if (code == 0) {
            return 0;
        }

        List<CategoryAttributeName> categoryAttributeNameList = this.categoryAttributeNameMapper.
                findByAttributeNameId(attributeName.getId());
        if (CollectionUtils.isNotEmpty(categoryAttributeNameList)) {
            Set<Long> oldCategorySet = new HashSet<>();
            for (CategoryAttributeName categoryAttributeName : categoryAttributeNameList) {
                oldCategorySet.add(categoryAttributeName.getCategoryId());
            }

            Set<Long> newCategorySet = new HashSet<>();
            for (Category category : attributeNameDto.getCategoryList()) {
                newCategorySet.add(category.getId());
            }

            Set<Long> deleteCategorySet = Sets.difference(oldCategorySet,newCategorySet);
            for (Long deleteCategoryId : deleteCategorySet) {
                Map<String,Object> map = new HashMap<>();
                map.put("catId",deleteCategoryId);
                map.put("attributeNameId",attributeName.getId());
                code = this.categoryAttributeNameMapper.deleteCategoryAttributeName(map);
                if (code == 0) {
                    return code;
                }
            }

            Set<Long> addCategorySet = Sets.difference(newCategorySet,oldCategorySet);
            for (Long addCategoryId : addCategorySet) {
                CategoryAttributeName categoryAttributeName = new CategoryAttributeName();
                categoryAttributeName.setCategoryId(addCategoryId);
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
        return code;
    }

    @Override
    public int delete(Long id) {
        // 如果有分类还在使用当前属性名，则不允许删除
        List<Category> categoryList = this.categoryMapper.findCategoriesByAttributeNameId(id);
        if (CollectionUtils.isNotEmpty(categoryList)) {
            return 0;
        }
        // 删除属性名
        int code = this.attributeNameMapper.delete(id);
        if (code == 0) {
            return code;
        }
        // 删除分类和属性名的关系
        return this.categoryAttributeNameMapper.deleteCategoryAttributeNameByAttributeNameId(id);
    }

    @Override
    public AttributeNameDto find(Long id) {
        AttributeName attributeName = this.attributeNameMapper.find(id);
        if (attributeName == null) {
            return null;
        }

        AttributeNameDto attributeNameDto = BeanUtils.copy(attributeName, AttributeNameDto.class);

        List<Category> categoryList = this.categoryMapper.findCategoriesByAttributeNameId(id);
        attributeNameDto.setCategoryList(categoryList);

        return attributeNameDto;
    }

    @Override
    public List<AttributeName> findAll() {
        return this.attributeNameMapper.findAll();
    }

    @Override
    public Long count() {
        return this.attributeNameMapper.count();
    }

    @Override
    public List<AttributeName> findAttributeNamesByCategoryId(Long categoryId) {

        return this.attributeNameMapper.findAttributeNameListByCategoryId(categoryId);
    }
}
