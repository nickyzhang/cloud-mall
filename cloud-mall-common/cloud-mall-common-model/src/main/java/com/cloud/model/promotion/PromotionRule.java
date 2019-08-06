package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionRule implements Serializable {

    private static final long serialVersionUID = 3309467482171883481L;

    /** 促销规则全局唯一标识符 */
    private Long ruleId;

    /** 促销规则名称 */
    private String ruleName;

    /** 促销规则类型 */
    private Integer ruleType;

    /** 梯度满减金额阀值,如果为0，没有阀值 */
    private Long ladderSpendMoney;

    /** 每满减金额阀值 */
    private Long perSpendMoney;

    /** 梯度满减数量阀值 */
    private Long ladderQuantity;

    /** 满减金额,可以是满XX元减YY元，或者满XX件减YY元 */
    private Long moneyOff;

    /** 满减折扣,可以是满XX元yy折扣，或者满XX件YY折扣*/
    private Long discountOff;

    /** 满M件送N件 */
    private Long numOff;

    /** 是否减免运费 */
    private Boolean freeShip;

    /** 减免运费的金额 */
    private Long freightOff;

    /** 是否赠送礼品 */
    private Boolean freeGift;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
