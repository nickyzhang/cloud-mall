package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionRuleSku extends BaseModel {

    private static final long serialVersionUID = 1918276120634442074L;

    private Long ruleId;

    private Long skuId;
}
