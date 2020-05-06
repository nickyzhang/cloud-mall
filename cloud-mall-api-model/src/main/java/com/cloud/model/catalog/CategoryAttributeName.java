package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CategoryAttributeName extends BaseModel {

    private static final long serialVersionUID = 3153523890088169544L;

    private Long categoryId;

    private Long attributeNameId;
}
