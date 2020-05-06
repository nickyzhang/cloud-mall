package com.cloud.dto.promotion;

import com.cloud.BaseModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CouponDTO extends BaseModel {

    private static final long serialVersionUID = 3922577960509748281L;

    private Long couponId;

    /** 对应的优惠券模板 */
    private Long templateId;

    /** 被谁领取的 */
    private Long userId;

    /** 优惠券码 */
    private String couponNo;

    /** 优惠券领取后的状态: 1 未使用 2 已使用 3 已过期 */
    private Integer couponStatus;

    /** 领取或者接收时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;
}
