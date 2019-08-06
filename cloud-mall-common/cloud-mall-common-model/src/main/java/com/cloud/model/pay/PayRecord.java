package com.cloud.model.pay;

import com.cloud.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PayRecord extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1671232316539368516L;

    private Long id;

    private Long buyerId;

    private Long orderId;

    private Long orderNo;

    private String tradeNo;

    private Integer orderStatus;

    private String subject;

    private String desc;

    private BigDecimal payAmount;

    private Integer payChannel;

    private BigDecimal payRatio;

    private BigDecimal merchantCost;

    private BigDecimal merchantIncome;

    private String merchantAccount;

    private LocalDateTime payTime;

    private Boolean refund;

    private BigDecimal refundAmount;

    private String notifyUrl;

    private LocalDateTime notifyTime;

    private String returnUrl;

    private Boolean deleted;
}
