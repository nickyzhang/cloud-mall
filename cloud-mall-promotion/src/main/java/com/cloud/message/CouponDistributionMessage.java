package com.cloud.message;

import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;

import java.io.Serializable;

@Data
public class CouponDistributionMessage implements Serializable {

    private static final long serialVersionUID = -7659296259462497784L;

    private Integer memberType;

    private CouponTemplate couponTemplate;

    public CouponDistributionMessage(){

    }

    public CouponDistributionMessage(Integer memberType, CouponTemplate couponTemplate) {
        this.memberType = memberType;
        this.couponTemplate = couponTemplate;
    }
}
