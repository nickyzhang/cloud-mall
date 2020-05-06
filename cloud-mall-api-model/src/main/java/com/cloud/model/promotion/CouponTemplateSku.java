package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponTemplateSku extends BaseModel{

    private static final long serialVersionUID = -6169980715758477014L;

    private Long skuId;

    private Long templateId;
}
