package com.cloud.model.order;

import com.cloud.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderAfterSales extends BaseModel {

    private static final long serialVersionUID = 1474123529079305884L;

    private Long serviceId;

    private Long orderId;

    private Long orderNo;
    /**
     * 售后状态
     * 1 待审核 2 待上门取件 3 待邮寄 4 待退货 5 待换货 6 退款中 7 已完成
     */
    private Integer afterStatus;

    /** 申请类型：1 退货 2 换货 */
    private Integer applyType;

    private String skuName;

    private Integer submitCount;

    private String imageUrl;

    private String reason;
    /** 返回方式 1 上门取件 2自寄 */
    private String returnMethod;

    private String takeAddress;

    private LocalDateTime takeTime;

    /** 1 PUTAWAY:退货入库;2 REDELIVERY:重新发货;3 RECLAIM-REDELIVERY:不要求归还并重新发货; 4 REFUND:退款; 5 COMPENSATION:不退货并赔偿 */
    private Integer handlingWay;

    private String receiverAddress;

    private String customerName;

    private String mobile;
}
