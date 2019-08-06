package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponSku implements Serializable{

    private static final long serialVersionUID = -6169980715758477014L;

    private Long couponSkuId;

    private Long skuId;

    private Long couponId;

    private Long productId;

    private String productName;

    private String skuName;

    private String skuCode;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
