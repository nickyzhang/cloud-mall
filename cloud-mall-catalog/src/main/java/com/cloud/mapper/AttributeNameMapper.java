package com.cloud.mapper;

import com.cloud.model.catalog.AttributeName;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface AttributeNameMapper {

    int save(AttributeName model);

    int saveList(List<AttributeName> modelList);

    int update(AttributeName model);

    int delete(Long id);

    AttributeName find(Long id);

    List<AttributeName> findAll();

    Long count();

    List<AttributeName> findAttributeNameListByCategoryId(Long categoryId);
}
