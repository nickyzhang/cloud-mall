package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryBrand extends BaseModel {

    private static final long serialVersionUID = -4842945332726825332L;

    private Long categoryId;

    private Long brandId;
}
