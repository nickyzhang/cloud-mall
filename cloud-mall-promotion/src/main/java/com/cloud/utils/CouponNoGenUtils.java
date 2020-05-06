package com.cloud.utils;

import com.cloud.common.core.utils.DateUtils;
import java.time.LocalDateTime;

public final class CouponNoGenUtils {

    /**
     * 优惠券编码生成规则: 时间6位+时间戳后4位+用户ID后四位
     *
     * @return
     */
    public static String genCouponCode(Long userId) {
        StringBuilder builder = new StringBuilder();
        LocalDateTime currentDateTime = LocalDateTime.now();
        String date = DateUtils.formatDateTime(currentDateTime,"yyMMdd");
        String milliSeconds = String.valueOf(DateUtils.milliSeconds(currentDateTime));
        String time = milliSeconds.substring(milliSeconds.length() - 4, milliSeconds.length());
        builder.append(date).append(time);
        String userIdStr = String.valueOf(userId);
        String subUserIdStr = userIdStr.substring(userIdStr.length() - 4, userIdStr.length());
        builder.append(subUserIdStr);
        return builder.toString();
    }
}
