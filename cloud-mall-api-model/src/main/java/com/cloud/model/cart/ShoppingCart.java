package com.cloud.model.cart;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 761082901915230925L;

    private Long cartId;

    private Long memberId;

    private Long skuId;

    private String skuName;

    private String skuPicture;

    private BigDecimal unitPrice;

    private BigDecimal beforeUnitPrice;

    private BigDecimal priceChange;

    private int quantity;

    private BigDecimal totalPrice;

    private boolean deleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
