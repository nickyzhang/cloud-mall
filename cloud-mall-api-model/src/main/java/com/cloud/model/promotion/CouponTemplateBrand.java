package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponTemplateBrand extends BaseModel {

    private static final long serialVersionUID = -7227501203435380157L;

    private Long templateId;

    private Long brandId;

}
