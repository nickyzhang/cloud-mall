package com.cloud.dto.promotion;

import com.cloud.BaseModel;
import com.cloud.model.promotion.PromotionRule;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CouponTemplateDTO extends BaseModel {

    private static final long serialVersionUID = 7534949905784903170L;

    /** 优惠券模板全局唯一标识符 */
    private Long templateId;

    /** 优惠券模板业务编码 */
    private Long templateNo;

    /** 优惠券状态: 1 新建 2 审核中 3 审核不通过 4 发行中 5 已下架 */
    private Integer templateStatus;

    /** 优惠券名称 */
    private String name;

    /** 优惠券描述 */
    private String desc;

    /** 优惠券图片 */
    private String imageUrl;

    /** 优惠券计算规则类型: 1 现金券 2 满减券 3 折扣券 */
    private Integer couponType;

    /** 是否允许和其他优惠，比如优惠活动叠加使用 */
    private boolean composition;


    /** 促销方式: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折
     *  8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠 */
    private Integer promotionMethod;

    /** 促销范围: 1 所有用户 2新用户 3 会员用户 4 男用户 5 女用户 6 自定义用户 */
    private Integer memberType;

    /** 平台类型限制: 1 平台券 2 店铺券 */
    private Integer platformType;

    /** 商品使用范围限制: 1 单品券 2 品牌券 3 品类券 */
    private Integer useScope;

    /** 使用渠道: 1 所有 2 主站 3 App 4 H5商城*/
    private Integer promotionChannel;

    /** 投放方式: 1 仅可发放 2 仅可领取 */
    private Integer launchMethod;

    /** 推广平台: 1 所有 2 主站+App+H5商城 3主站 4 App 5 H5商城 6 手机 7 邮件 */
    private Integer promotionPlatform;

    /** 优惠券面值: 如果金额类就是金额面值；如果是折扣类就是折扣面值 */
    private Integer faceValue;
    /** 优惠券折扣(折扣类优惠券) */
    private Integer discount;

    /** 优惠券发行数量 */
    private Long issueNum;

    /** 优惠券已经领取的数量 */
    private Long receiveNum;

    /** 每一个用户限领数量,默认是1张 */
    private int receiveNumLimit;

    /** 优惠券已经使用的数量 */
    private Long usedNum;

    /** 优惠券已经使用的金额 */
    private BigDecimal usedAmount;

    /** 优惠券金额限制，每一张优惠券不能超过这个额度 */
    private BigDecimal amountLimit;

    /** 优惠券发行时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime issueTime;

    /** 优惠券到期时间 */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "/catalogyyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    /** 优惠券有效天数 */
    private Integer validDays;

    /** 优惠券对应的规则 */
    private PromotionRule promotionRule;

    /** 优惠券对应的SKU列表 */
    private List<Long> skuList;

    /** 优惠券对应的SKU对应的品类 */
    private List<Long> catList;

    /** 优惠券对应的SKU对应的品牌 */
    private List<Long> brandList;
}
