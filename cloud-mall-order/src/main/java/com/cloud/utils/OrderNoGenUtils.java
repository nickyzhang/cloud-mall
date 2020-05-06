package com.cloud.utils;

import com.cloud.common.core.utils.DateUtils;
import java.time.LocalDateTime;

public class OrderNoGenUtils {

    /**
     * 平台(1)+渠道(1)+支付渠道(1)+订单类型(1)+日期(6)+时间戳(4)+用户ID(4)
     * 订单号最好控制在14-20位之间，如果订单量很少的，可以控制在10位
     * 在这里简单化: 渠道+类型+日期+时间戳+用户ID
     */
    public static Long genOrderNo(Integer channel, Integer orderType, Long userId) {
        StringBuilder orderNoBuilder = new StringBuilder();
        String userIdStr = String.valueOf(userId);
        String subUserIdStr = userIdStr.substring(userIdStr.length() - 4, userIdStr.length());
        orderNoBuilder.append(subUserIdStr);
        orderNoBuilder.append(channel).append(orderType);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String date = DateUtils.formatDateTime(currentDateTime,"yyMMdd");
        String milliSeconds = String.valueOf(DateUtils.milliSeconds(currentDateTime));
        String time = milliSeconds.substring(milliSeconds.length() - 4, milliSeconds.length());
        orderNoBuilder.append(date).append(time);
        return Long.valueOf(orderNoBuilder.toString());
    }
}
