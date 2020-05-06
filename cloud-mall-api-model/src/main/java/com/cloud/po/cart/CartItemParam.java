package com.cloud.po.cart;

import lombok.Data;
import java.io.Serializable;

@Data
public class CartItemParam implements Serializable {

    private Long cartId;

    private Long skuId;

    private Integer quantity;
}
