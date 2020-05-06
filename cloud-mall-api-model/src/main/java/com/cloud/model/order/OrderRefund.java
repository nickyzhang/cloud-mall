package com.cloud.model.order;

import com.cloud.BaseModel;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderRefund extends BaseModel{

    private static final long serialVersionUID = 69006929895785724L;

    private Long refundId;

    private Long serviceId;

    private Long orderId;

    private Long orderNo;

    private BigDecimal refundAmount;

    private Integer refundStatus;

    private LocalDateTime arriveTime;
}
