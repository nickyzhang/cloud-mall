package com.cloud.vo.catalog;

import com.cloud.BaseModel;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.AttributeValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttributeValueVo extends BaseModel {

    private static final long serialVersionUID = -3403652888594247444L;

    private Long id;

    private String value;

    private int displayOrder;

    private AttributeName name;
}
