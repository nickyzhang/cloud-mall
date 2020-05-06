package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivitySku extends BaseModel{

    private static final long serialVersionUID = -3596309749546302503L;

    private Long activityId;

    private Long skuId;
}
