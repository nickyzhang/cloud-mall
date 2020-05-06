package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SkuProperties extends BaseModel {

    private static final long serialVersionUID = -5909538659884362810L;

    private Long skuId;

    private Long attributeValueId;
}
