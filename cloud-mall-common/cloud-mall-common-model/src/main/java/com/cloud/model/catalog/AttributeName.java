package com.cloud.model.catalog;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AttributeName implements Serializable {

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Category> categoryList;

    public AttributeName() {
    }

    public AttributeName(Long id, String name, int type, String fieldName, String fieldType, int inputType, boolean required, String group, int displayOrder, int status, boolean skuAttribute, LocalDateTime createTime, LocalDateTime updateTime, List<Category> categoryList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.inputType = inputType;
        this.required = required;
        this.group = group;
        this.displayOrder = displayOrder;
        this.status = status;
        this.skuAttribute = skuAttribute;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.categoryList = categoryList;
    }
}
