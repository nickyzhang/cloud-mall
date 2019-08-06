package com.cloud.model.cart;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartItem implements Serializable {

    private static final long serialVersionUID = 5326214087731162409L;

    /** ProductId */
    private Long productId;

    /** SkuId */
    private Long skuId;

    /** SKU名称 */
    private String skuName;

    /** SKU图片 */
    private String picture;

    /** SKU价格 */
    private BigDecimal price;

    /** 加入购物车的数量 */
    private Long quantity;

    /** 总金额 */
    private BigDecimal amount;

    /** 支付总额 */
    private BigDecimal paymentAmount;

    /** 该商品是否已经下架 */
    private boolean status;
}
