package com.cloud.vo.promotion;

import com.cloud.BaseModel;
import com.cloud.model.promotion.PromotionRuleSku;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PromotionRuleVO extends BaseModel {

    private static final long serialVersionUID = 9166315344088264715L;

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
    private Boolean freeShip;

    /** 减免运费的金额 */
    private Integer freightOff;

    /** 是否赠送礼品 */
    private boolean freeGift;

    /** 赠送的礼品列表 */
    private List<Long> giftList;
}
