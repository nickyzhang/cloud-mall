package com.cloud.dto.promotion;

import com.cloud.model.promotion.PromotionRule;
import com.cloud.model.promotion.PromotionRuleSku;
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
public class PromotionRuleDTO implements Serializable {

    private static final long serialVersionUID = 5566234542299349112L;

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

    /** 逻辑删除 */
    private boolean deleted;

    /** 创建时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /** 赠送的礼品列表 */
    private List<Long> giftList;
}
