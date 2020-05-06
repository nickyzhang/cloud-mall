package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AttributeValue extends BaseModel {

    private static final long serialVersionUID = 7521151377746574187L;

    private Long id;

    private String value;

    private int displayOrder;

    private AttributeName name;
}
