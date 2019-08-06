package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Coupon implements Serializable {

    private static final long serialVersionUID = -3717738932554197344L;

    /** 优惠券全局唯一标识符 */
    private Long couponId;

    /** 优惠券业务编码 */
    private String couponNo;

    /** 优惠券名称 */
    private String name;

    /** 优惠券描述 */
    private String description;

    /** 优惠券的优先级，默认为1 */
    private Integer priority;

    /** 优惠券计算规则类型: 1 现金券 2 满减券 3 折扣券 */
    private Integer couponType;

    /** 促销方式: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折
     *  8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠 */
    private Integer promotionMethod;

    /** 促销范围: 1 所有用户 2新用户 3 会员用户 4 男用户 5 女用户 6 自定义用户 */
    private Integer promotionScope;

    /** 平台类型限制: 1 平台券 2 店铺券 */
    private Integer platformType;

    /** 商品使用范围限制: 1 单品券 2 品牌券 3 品类券 */
    private Integer useScope;

    /** 使用渠道: 1 所有 2 主站 3 App 4 H5商城*/
    private Integer promotionChannel;

    /** 投放方式: 1 可发放可领取 2 仅可发放 3 仅可领取 */
    private Integer launchMethod;

    /** 推广平台: 1 所有 2 主站+App+H5商城 3主站 4 App 5 H5商城 6 手机 7 邮件 */
    private Integer promotionPlatform;

    /** 优惠券面值 */
    private Long faceValue;

    /** 发行数量 */
    private Long issueNum;

    /** 剩余库存数量 */
    private Long stockNum;

    /** 优惠券是否公开 */
    private boolean open;

    /** 优惠券状态: 1 新建 2 审核中 3 审核不通过 4 待发布 5 已发行 6 已作废 7 已下架 */
    private Integer couponStatus;

    /** 优惠券金额限制，不能超过这个额度 */
    private Long amountLimit;

    /** 每一个用户限领数量,默认是1张 */
    private Long receiveNumLimit;

    /** 优惠券发行时间 */
    private LocalDateTime issueTime;

    /** 优惠券到期时间 */
    private LocalDateTime expireTime;

    /** 优惠券有效天数 */
    private Integer validDays;

    /** 优惠券是否有效 */
    private Boolean valid;

    /** 创建日期 */
    private LocalDateTime createTime;

    /** 更新日期 */
    private LocalDateTime updateTime;

    /** 优惠券对应的规则 */
    private PromotionRule promotionRule;
}
