package com.cloud.po.pay;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GoodsParam implements Serializable {

    private static final long serialVersionUID = -1749813683052195684L;

    private Long goodsId;

    private String goodsName;

    private int quantity;

    private BigDecimal price;
}
