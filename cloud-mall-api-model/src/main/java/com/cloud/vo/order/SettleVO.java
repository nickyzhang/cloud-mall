package com.cloud.vo.order;

import com.cloud.BaseModel;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.user.ShippingAddress;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SettleVO extends BaseModel {

    private static final long serialVersionUID = 6184158843569581740L;

    private Long cartId;

    private Long memberId;

    private String shippingMethod;

    private BigDecimal freight;

    private BigDecimal totalAmount;

    private List<SettleItemVO> itemList;

    private List<Coupon> couponList;

    private BigDecimal totalActSavedMoney;

    private BigDecimal totalCouponSavedMoney;

    private List<ShippingAddress> addressList;
}
