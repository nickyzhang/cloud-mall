package com.cloud.dto.promotion;

import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponCategory;
import com.cloud.model.promotion.CouponSku;
import lombok.Data;
import java.util.List;

@Data
public class CouponDTO extends Coupon {

    private static final long serialVersionUID = 3922577960509748281L;

    /** 优惠券对应的SKU列表 */
    private List<CouponSku> couponSkuList;

    /** 优惠券对应的SKU对应的品类 */
    private List<CouponCategory> couponCategoryList;
}
