package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.Brand;
import com.cloud.model.catalog.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BrandDto extends BaseModel {

    private static final long serialVersionUID = -5588652765255778951L;

    private Long id;

    private String chineseName;

    private String englishName;

    private String description;

    private String logo;

    private String origin;


    private List<Category> categoryList;
}
