package com.cloud.model.promotion;

import com.cloud.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PromotionRule extends BaseModel {

    private static final long serialVersionUID = 3309467482171883481L;

    /** 促销规则全局唯一标识符 */
    private Long ruleId;

    /** 促销规则名称 */
    private String ruleName;

    /** 促销规则类型 */
    private Integer ruleType;

    /** 梯度满减金额阀值,如果为0，没有阀值 */
    private Integer ladderSpendMoney;

    /** 每满减金额阀值 */
    private Integer perSpendMoney;

    /** 梯度满减数量阀值 */
    private Integer ladderQuantity;

    /** 满减金额,可以是满XX元减YY元，或者满XX件减YY元 */
    private Integer moneyOff;

    /** 满减折扣,可以是满XX元yy折扣，或者满XX件YY折扣*/
    private Integer discountOff;

    /** 满M件送N件 */
    private Integer numOff;

    /** 是否减免运费 */
    private boolean freeShip;

    /** 减免运费的金额 */
    private Integer freightOff;

    /** 是否赠送礼品 */
    private boolean freeGift;
}
