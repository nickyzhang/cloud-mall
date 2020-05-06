package com.cloud.model.pay;

import com.cloud.BaseModel;
import com.cloud.model.AbstractModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RefundRecord extends BaseModel {

    private static final long serialVersionUID = -8053952890848315245L;

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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime notifyTime;
}
