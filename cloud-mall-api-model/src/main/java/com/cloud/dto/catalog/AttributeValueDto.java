package com.cloud.dto.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.AttributeValue;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AttributeValueDto extends BaseModel {

    private static final long serialVersionUID = 7020164266906203718L;

    private Long id;

    private String value;

    private int displayOrder;

    private AttributeName name;
}
