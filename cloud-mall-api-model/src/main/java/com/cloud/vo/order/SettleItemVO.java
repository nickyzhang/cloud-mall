package com.cloud.vo.order;

import com.cloud.vo.catalog.ItemSpec;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SettleItemVO implements Serializable {

    private Long skuId;

    private String skuName;

    private String skuPicture;

    private BigDecimal unitPrice;

    private int quantity;

    private boolean stock;

    private BigDecimal originSubTotal;

    private List<Activity> activityList;

    private List<CouponTemplate> templateList;

    private List<Coupon> couponList;

    private BigDecimal subTotal;

    private BigDecimal actSavedMoney;

    private List<ItemSpec> specList;
}
