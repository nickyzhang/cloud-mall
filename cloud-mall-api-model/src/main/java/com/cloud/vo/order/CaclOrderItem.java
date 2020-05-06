package com.cloud.vo.order;

import com.cloud.model.promotion.CouponTemplate;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CaclOrderItem implements Serializable {

    private Long skuId;

    private BigDecimal amount;

    private List<CouponTemplate> templateList;
}
