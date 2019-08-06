package com.cloud.model.pay;

import com.cloud.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RefundRecord extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -8053952890848315245L;

    private Long id;

    private Long buyerId;

    private Long orderId;

    private Long orderNo;

    private String tradeNo;

    private Integer orderStatus;

    private String subject;

    private String desc;

    private BigDecimal refundAmount;

    private Integer refundChannel;

    private Integer refundStatus;

    private String notifyUrl;

    private LocalDateTime notifyTime;

    private Boolean deleted;
}
