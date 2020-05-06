package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AttributeName extends BaseModel {

    private static final long serialVersionUID = -919515879225624929L;

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
}
