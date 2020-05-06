package com.cloud.dto.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderRefundDTO extends BaseModel{

    private static final long serialVersionUID = -5611665615218154360L;

    private Long refundId;

    private Long serviceId;

    private Long orderId;

    private Long orderNo;

    private BigDecimal refundAmount;

    private Integer refundStatus;

    private LocalDateTime arriveTime;
}
