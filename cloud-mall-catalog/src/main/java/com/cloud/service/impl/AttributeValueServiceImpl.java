package com.cloud.service.impl;

import com.cloud.common.core.utils.BeanUtils;
import com.cloud.dto.catalog.AttributeValueDto;
import com.cloud.mapper.*;
import com.cloud.model.catalog.AttributeValue;
import com.cloud.service.AttributeValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
    public class AttributeValueServiceImpl implements AttributeValueService {

    private static Logger logger = LoggerFactory.getLogger(AttributeValueServiceImpl.class);

    @Autowired
    AttributeValueMapper attributeValueMapper;


    @Override
    public int save(AttributeValueDto attributeValueDto) {
        // 只需要单向的保存自己,无需关心是product还是sku来关联,也就不需要由它来维护关系
        if (attributeValueDto == null) {
            return 0;
        }
        AttributeValue attributeValue = BeanUtils.copy(attributeValueDto,AttributeValue.class);
        return this.attributeValueMapper.save(attributeValue);
    }

    @Override
    public int saveList(List<AttributeValue> attributeValueList) {
        return this.attributeValueMapper.saveList(attributeValueList);
    }

    @Override
    public int update(AttributeValueDto attributeValueDto) {
        // 只需要单向的更新自己,无需关心是product还是sku来关联
        if (attributeValueDto == null) {
            return 0;
        }
        AttributeValue attributeValue = BeanUtils.copy(attributeValueDto,AttributeValue.class);
        return this.attributeValueMapper.update(attributeValue);
    }

    @Override
    public int delete(Long id) {
        return this.attributeValueMapper.delete(id);
    }

    @Override
    public AttributeValueDto find(Long id) {
        AttributeValue attributeValue = this.attributeValueMapper.find(id);
        if (attributeValue == null) {
            return null;
        }

        return BeanUtils.copy(attributeValue,AttributeValueDto.class);
    }

    @Override
    public List<AttributeValue> findAll() {
        return this.attributeValueMapper.findAll();
    }

    @Override
    public Long count() {
        return this.attributeValueMapper.count();
    }

    @Override
    public List<AttributeValue> findAttributeValueListByAttributeNameId(Long attributeNameId) {

        return this.attributeValueMapper.findAttributeValueListByAttributeNameId(attributeNameId);
    }

    @Override
    public List<AttributeValue> findAttributeValueListByProductId(Long productId) {

        return this.attributeValueMapper.findAttributeValueListByProductId(productId);
    }

    @Override
    public List<AttributeValue> findAttributeValueListBySkuId(Long skuId) {

        return this.attributeValueMapper.findAttributeValueListBySkuId(skuId);
    }
}
