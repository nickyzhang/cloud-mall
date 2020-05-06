package com.cloud.model.promotion;

import com.cloud.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponUseRecord extends BaseModel {

    private static final long serialVersionUID = 7172548716783272475L;

    /** 优惠券使用记录id */
    private Long id;

    /** 优惠券对应的模板id */
    private Long templateId;

    /** 用户ID */
    private Long userId;

    /** 优惠券ID */
    private Long couponId;

    /** 优惠券业务代码 */
    private String couponNo;

    /** 订单号 */
    private Long orderId;

    /** 订单业务号 */
    private String orderNo;

    /** 使用的金额 */
    private BigDecimal usedAmount;

    /** 使用日期 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

}
