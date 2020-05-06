package com.cloud.po.pay;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PayParam implements Serializable {

    private String subject;

    private Integer expiredMinutes;

    private int payType;

    private Long outTradeNo;

    private BigDecimal tradeAmount;

    private Long orderNo;

    private Long orderId;

    private int orderType;

    private LocalDateTime orderDateTime;

    private List<GoodsParam> items;

    private String logistics;

    private String notifyUrl;

    private String returnUrl;

    private String currency;

    private String sign;

    private String signType;
}
