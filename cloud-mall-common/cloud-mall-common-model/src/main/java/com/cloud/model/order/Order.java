package com.cloud.model.order;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order implements Serializable{

    private static final long serialVersionUID = 8779191821490013837L;
    /**
     * 订单表主键
     * 因为你用关联，如果订单关联商品，那么订单中商品信息就会同步修改，导致下单前后商品信息不一致。
     有些时候是用关联，有些时候用订单表里面的商品数据，根据需求来订的。
     */
    private Long orderId;

    /**
     * 订单编号，生成规则：渠道(1)+订单类型(1)+日期(4)位+Unix时间戳(4）位+用户id(5)
     */
    private Long orderNo;

    /**
     * 支付生成的交易号
     */
    private Long tradeNo;

    /**
     * 父订单
     */
    private Long parentId;

    /**
     * 用户信息
     */
    private Long userId;

    /**
     * 订单类型
     * 1: 正常订单 2: 秒杀订单
     */
    private Integer orderType;

    /**
     * 订单状态
     * 0: 待付款, 1: 待发货 2: 待收货 3: 已完成 4: 已取消 5: 售后中 6: 已关闭
     */
    private Integer orderStatus;

    /**
     * 用户售后状态
     * 0:待审核 1:待退货入库 2:待退款 3:待换货入库 4:换货出库中 5:售后成功
     */
    private Integer afterStatus;

    /**
     * 订单创建渠道： 1: PC 2:Mobile
     */
    private Integer orderChannel;

    /** 总金额用分表示 */
    private Long totalAmount;

    /** 运费用分表示 */
    private Long freightAmount;

    /** 支付金额 */
    private Long paymentAmount;

    /** 促销总金额 */
    private Long promotionAmount;

    /** 订单明细 */
    private List<OrderItem> orderItemList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime paymentTime;

    private LocalDateTime deliveryTime;

    private LocalDateTime receiveTime;
}
