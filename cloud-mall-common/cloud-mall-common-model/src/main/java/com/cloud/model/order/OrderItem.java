package com.cloud.model.order;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1169620512999961569L;

    private Long itemId;

    private Long orderId;

    private Long orderNo;

    private Long skuId;

    private String title;

    private Long price;

    private Integer stockStatus;

    private String imageUrl;

    private Long couponId;

    private Long invoiceId;

    private Integer quantity;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;






}
