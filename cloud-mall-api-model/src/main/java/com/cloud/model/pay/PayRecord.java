package com.cloud.model.pay;

import com.cloud.model.AbstractModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PayRecord extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1671232316539368516L;

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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    private Boolean refund;

    private BigDecimal refundAmount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime notifyTime;

    private String returnUrl;

    private String notifyUrl;
}
