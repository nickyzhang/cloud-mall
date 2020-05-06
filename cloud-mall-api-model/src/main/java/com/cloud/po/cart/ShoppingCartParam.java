package com.cloud.po.cart;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShoppingCartParam implements Serializable {

    private static final long serialVersionUID = -7940073123028978446L;

    private Long memberId;

    private Long skuId;

    private String skuName;

    private String skuPicture;

    private BigDecimal unitPrice;

    private BigDecimal beforeUnitPrice;

    private BigDecimal priceChange;

    private int quantity;

    private BigDecimal totalPrice;

}
