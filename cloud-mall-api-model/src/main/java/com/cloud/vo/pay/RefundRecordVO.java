package com.cloud.vo.pay;

import com.cloud.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundRecordVO extends BaseModel {

    private static final long serialVersionUID = 724689905767199697L;

    private Long id;

    private Long buyerId;

    private Long orderId;

    private Long orderNo;

    private String tradeNo;

    private Integer orderStatus;

    private String subject;

    private BigDecimal refundAmount;

    private Integer refundChannel;

    private Integer refundStatus;

    private String notifyUrl;

    private LocalDateTime notifyTime;
}
