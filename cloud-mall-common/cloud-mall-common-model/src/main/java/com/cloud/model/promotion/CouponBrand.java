package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponBrand implements Serializable{

    private static final long serialVersionUID = -2540598996241369228L;

    private Long couponBrandId;

    private Long brandId;

    private Long couponId;

    private String brandChineseName;

    private String brandEnglishName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
