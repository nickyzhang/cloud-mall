package com.cloud.vo.catalog;

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
public class CategoryVo extends BaseModel {

    private static final long serialVersionUID = 5081897212217138451L;

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
