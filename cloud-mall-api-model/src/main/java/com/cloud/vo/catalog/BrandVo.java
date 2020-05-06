package com.cloud.vo.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.Category;
import lombok.Data;
import java.util.List;

@Data
public class BrandVo extends BaseModel {

    private static final long serialVersionUID = -5124339537147818520L;

    private Long id;

    private String chineseName;

    private String englishName;

    private String description;

    private String logo;

    private String origin;

    private List<Category> categoryList;
}
