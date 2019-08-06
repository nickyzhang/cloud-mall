package com.cloud.model.promotion;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Activity implements Serializable{

    private static final long serialVersionUID = -3311250769296221696L;

    /** 促销活动全局唯一标识符 */
    private Long activityId;

    /** 促销活动名称 */
    private String name;

    /** 促销活动描述 */
    private String description;

    /** 促销活动图片路径 */
    private String imageUrl;

    /** 促销活动链接 */
    private String promotionLink;

    /** 促销活动状态: 1 未开始 2 进行中 3 已结束 */
    private Integer activityStatus;

    /** 促销活动类型: 1 满减促销 2 单品促销 3 赠品促销 4 满赠促销 5 多买优惠 6 运费促销 */
    private Integer activityType;

    /** 促销活动方式: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折
     *  8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠 */
    private Integer promotionMethod;

    /** 促销范围: 1 所有用户 2新用户 3 会员用户 4 男用户 5 女用户 6 自定义用户 */
    private Integer promotionScope;

    /** 推广平台: 1 所有 2 主站+App+H5商城 3主站 4 App 5 H5商城 6 手机 7 邮件 */
    private Integer promotionPlatform;

    /** 促销渠道: 1 所有 2 主站 3 App 4 H5 */
    private Integer promotionChannel;

    /** 用户购买活动商品数量限制 */
    private Integer quantityLimit;

    /** 促销活动商品库存数量限制，是否允许所有库存参与活动 */
    private Long stockLimit;

    /** 促销活动标签 */
    private String activityTags;

    /** 活动开始时间 */
    private LocalDateTime startTime;

    /** 活动结束时间 */
    private LocalDateTime endTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
