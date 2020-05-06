package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponTemplateCat extends BaseModel {

    private static final long serialVersionUID = 4430423022825975244L;

    private Long templateId;

    private Long catId;
}
