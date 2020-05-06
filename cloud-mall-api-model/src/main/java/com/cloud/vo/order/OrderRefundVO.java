package com.cloud.vo.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderRefundVO extends BaseModel{

    private static final long serialVersionUID = -7743281786332858567L;

    private Long refundId;

    private Long serviceId;

    private Long orderId;

    private Long orderNo;

    private BigDecimal refundAmount;

    private Integer refundStatus;

    private LocalDateTime arriveTime;
}
