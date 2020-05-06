package com.cloud.po.order;

import com.cloud.vo.catalog.ItemSpec;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemReq implements Serializable {

    private Long skuId;

    private String skuName;

    private String skuPicture;

    private BigDecimal unitPrice;

    private int quantity;

    private boolean stock;

    private List<ItemSpec> specList;

    private BigDecimal originSubTotal;

    private BigDecimal subTotal;

    // 可能有多个活动
    private List<Activity> activityList;

    private BigDecimal activitySavedMoney;

    private List<CouponTemplate> templateList;
}
