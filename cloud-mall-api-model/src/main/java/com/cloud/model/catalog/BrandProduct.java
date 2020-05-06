package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BrandProduct extends BaseModel {

    private static final long serialVersionUID = -4550587822094720500L;

    private Long brandId;

    private Long productId;
}
