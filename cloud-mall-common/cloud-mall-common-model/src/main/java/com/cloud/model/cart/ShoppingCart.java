package com.cloud.model.cart;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 9003322733762320497L;

    private Long userId;

    private List<CartItem> itemList;
}
