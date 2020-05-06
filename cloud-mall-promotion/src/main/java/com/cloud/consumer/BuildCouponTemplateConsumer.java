package com.cloud.consumer;

import com.cloud.api.UserDetailsService;
import com.cloud.api.UserService;
import com.cloud.common.core.bean.ResponseResult;
import com.cloud.common.core.service.GenIdService;
import com.cloud.dto.promotion.CouponDTO;
import com.cloud.enums.CouponStatus;
import com.cloud.enums.MemberType;
import com.cloud.message.CouponDistributionMessage;
import com.cloud.model.promotion.Coupon;
import com.cloud.model.promotion.CouponTemplate;
import com.cloud.service.CouponService;
import com.cloud.utils.CouponNoGenUtils;
import com.cloud.vo.promotion.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "coupon.build",durable = "false",autoDelete = "true",exclusive = "false"),
        exchange = @Exchange(value = "coupon.build",type = "direct", durable = "false",autoDelete = "true"),
        key = "coupon.build"
))
public class BuildCouponTemplateConsumer {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    CouponService couponService;

    @Autowired
    GenIdService genIdService;

    @RabbitHandler
    public void handle(@Payload CouponDistributionMessage message){
        try {
            log.info("[BuildCouponTemplateConsumer] 接收消息,开始处理消息");
            Integer memberType = message.getMemberType();
            if (memberType == MemberType.ALL.getCode()) {
                Long start = Long.valueOf((int)(Math.random()*(1000000)));
                Long offset = message.getCouponTemplate().getIssueNum();
                log.info("用户区间: start->"+start+"; offset->"+offset);
                ResponseResult<List<Long>> result = this.userService.findUserListByLimit(start,offset);
                List<Long> allUserList = result.getData();
                buildTemplate(allUserList,message.getCouponTemplate());
            } else if (memberType == MemberType.MALE_USER.getCode()) {
                ResponseResult<List<Long>> result = this.userDetailsService.findUserIdListByGender("男");
                List<Long> allMenList = result.getData();
                buildTemplate(allMenList,message.getCouponTemplate());
            } else if (memberType == MemberType.FEMALE_USER.getCode()) {
                ResponseResult<List<Long>> result = this.userDetailsService.findUserIdListByGender("女");
                List<Long> allWomenList = result.getData();
                buildTemplate(allWomenList,message.getCouponTemplate());
            } else if (memberType == MemberType.NEW_USER.getCode()){

            } else if (memberType == MemberType.VIP_USER.getCode()) {

            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void buildTemplate(List<Long> userIds, CouponTemplate template) {
        Coupon coupon = null;
        List<Coupon> couponList = new ArrayList<>(userIds.size());
        LocalDateTime issueTime = template.getIssueTime();
        int days = template.getValidDays();
        LocalDateTime expiredTime = issueTime.plusDays(days);
        LocalDateTime now = LocalDateTime.now();
        for (Long userId : userIds) {
            coupon = new Coupon();
            coupon.setCouponId(this.genIdService.genId());
            coupon.setCouponNo(CouponNoGenUtils.genCouponCode(userId));
            coupon.setCouponStatus(CouponStatus.UNUSED.getCode());
            coupon.setUserId(userId);
            coupon.setTemplate(template);
            coupon.setExpiredTime(expiredTime);
            coupon.setReceiveTime(now);
            coupon.setDeleted(false);
            coupon.setCreateTime(now);
            coupon.setUpdateTime(now);
            couponList.add(coupon);
        }
        this.couponService.saveList(couponList);
    }
}
