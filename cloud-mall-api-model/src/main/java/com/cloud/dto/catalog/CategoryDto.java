package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.Brand;
import com.cloud.model.catalog.Category;
import com.cloud.model.catalog.Product;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryDto extends BaseModel {

    private static final long serialVersionUID = 1324959124099562649L;

    private Long id;

    private Long parentId;

    private String name;

    private String description;

    private String imagePath;

    private int displayOrder;

    private List<Brand> brandList;

    private List<Product> productList;

    private List<AttributeName> attributeNameList;
}
