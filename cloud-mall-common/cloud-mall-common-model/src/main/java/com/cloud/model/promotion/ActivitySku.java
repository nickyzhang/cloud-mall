package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivitySku implements Serializable{

    private static final long serialVersionUID = -3596309749546302503L;

    private Long activitySkuId;

    private Long activityId;

    private Long skuId;

    private Long productId;

    private String productName;

    private String skuName;

    private String skuCode;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
