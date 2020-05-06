package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProductProperties extends BaseModel {

    private static final long serialVersionUID = 2252481085248229326L;

    private Long productId;

    private Long attributeValueId;
}
