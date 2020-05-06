package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.Category;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AttributeNameDto extends BaseModel {

    private static final long serialVersionUID = 8553565798517227187L;

    private Long id;

    private String name;

    private int type;

    private String fieldName;

    private String fieldType;

    private int inputType;

    private boolean required;

    private String group;

    private int displayOrder;

    private int status;

    private boolean skuAttribute;

    private List<Category> categoryList;
}
