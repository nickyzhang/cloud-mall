package com.cloud.po.pay;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderParam implements Serializable{

    private static final long serialVersionUID = 401935949740025943L;

    /**
     * 订单表主键
     */
    private Long orderId;

    /**
     * 订单编号，生成规则：渠道(1)+订单类型(1)+日期(4)位+Unix时间戳(4）位+用户id(5)
     */
    private Long orderNo;

    /**
     * 用户信息
     */
    private Long userId;

    /**
     * 支付生成的交易号
     */
    private Long tradeNo;

    /**
     * 订单类型
     * 1: 正常订单 2: 秒杀订单
     */
    private Integer orderType;

    /**
     * 订单状态
     * 订单状态(1 待付款,2 待发货,3 待收货, 4 已完成 5 已取消 6 售后中 7 已关闭 8 待评价 9 已评价)
     */
    private Integer status;

    /**
     * 配送方式
     */
    private Integer shippingMethod;

    /**
     * 订单创建渠道： 1: PC 2:Mobile 3:Tablet
     */
    private Integer channel;

    /** 总金额 */
    private BigDecimal totalAmount;

    /** 运费 */
    private BigDecimal freight;

    /** 活动优惠金额 */
    private BigDecimal activityAmount;

    /** 商品优惠券总金额 */
    private BigDecimal couponAmount;

    /** 应支付金额 */
    private BigDecimal paymentAmount;

    /** 订单支付时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;

    /** 订单发货时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryTime;

    /** 订单发收货时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;
}
