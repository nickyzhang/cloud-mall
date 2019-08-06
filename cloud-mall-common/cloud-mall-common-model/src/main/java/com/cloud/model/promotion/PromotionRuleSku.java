package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionRuleSku implements Serializable {

    private static final long serialVersionUID = 1918276120634442074L;

    private Long id;

    private Long ruleId;

    private Long skuId;

    private Long productId;

    private String productName;

    private String skuName;

    private String skuCode;

    private String imageUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
