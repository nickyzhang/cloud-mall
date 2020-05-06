package com.cloud.model.catalog;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class SkuBundle extends BaseModel {
    private static final long serialVersionUID = 2346552484420844883L;

    private Long skuId;

    private Long bundleId;
}
