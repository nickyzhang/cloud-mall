package com.cloud.vo.cart;

import com.cloud.vo.catalog.ItemSpec;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ShoppingCartInfo implements Serializable {

    private static final long serialVersionUID = -783806913643164934L;

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

    private boolean stock;

    private List<ItemSpec> specList;

    private List<CouponTemplate> templateList;

    private List<Activity> activityList;

    private BigDecimal actSavedMoney;

}
