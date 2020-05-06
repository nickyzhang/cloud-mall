package com.cloud.vo.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.Category;
import lombok.Data;
import java.util.List;

@Data
public class AttributeNameVo extends BaseModel {

    private static final long serialVersionUID = 4058322722250044024L;

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
