package com.cloud.dto.pay;

import com.cloud.BaseModel;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayRecordDTO extends BaseModel {

    private static final long serialVersionUID = -5717643291353808911L;
    private Long tradeId;

    private Long buyerId;

    private Long orderId;

    private Long orderNo;

    private String tradeNo;

    private Integer tradeStatus;

    private Integer orderStatus;

    private String subject;

    private BigDecimal payAmount;

    private Integer payChannel;

    private BigDecimal payRatio;

    private BigDecimal merchantCost;

    private BigDecimal merchantIncome;

    private String merchantAccount;

    private LocalDateTime payTime;

    private Boolean refund;

    private BigDecimal refundAmount;

    private LocalDateTime notifyTime;

    private String returnUrl;

    private String notifyUrl;
}