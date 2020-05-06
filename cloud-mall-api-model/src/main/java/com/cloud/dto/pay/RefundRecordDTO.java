package com.cloud.dto.pay;

import com.cloud.BaseModel;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundRecordDTO extends BaseModel {

    private static final long serialVersionUID = -3558892065808384080L;

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
