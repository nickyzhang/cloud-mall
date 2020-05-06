package com.cloud.vo.pay;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayRecordVO implements Serializable {

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
