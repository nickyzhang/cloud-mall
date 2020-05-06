package com.cloud.vo.promotion;

import com.cloud.model.catalog.Sku;
import com.cloud.model.promotion.Coupon;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CouponCaclVO implements Serializable {

    private static final long serialVersionUID = -6679902057423466023L;

    private Coupon coupon;

    private List<Long> skuList;

    private BigDecimal savedAmount;
}
