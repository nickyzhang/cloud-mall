package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CouponCategory implements Serializable{

    private static final long serialVersionUID = 7410459514990290673L;

    private Long couponCategoryId;

    private Long categoryId;

    private Long couponId;

    private String categoryName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
