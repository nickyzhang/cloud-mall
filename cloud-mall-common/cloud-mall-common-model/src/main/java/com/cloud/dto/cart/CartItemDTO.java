package com.cloud.dto.cart;

import com.cloud.model.cart.CartItem;
import com.cloud.model.catalog.AttributeName;
import com.cloud.model.catalog.AttributeValue;
import com.cloud.model.promotion.Activity;
import com.cloud.model.promotion.Coupon;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class CartItemDTO extends CartItem {

    private static final long serialVersionUID = -1209259187726357843L;

    /** 商品有哪些可以领取的优惠券 */
    private List<Coupon> couponList;

    /** 该商品参与了哪些活动 */
    private List<Activity> activityList;

    /** 商品有哪些销售属性 */
    private Map<AttributeName,AttributeValue> saleAttributeMap;
}
