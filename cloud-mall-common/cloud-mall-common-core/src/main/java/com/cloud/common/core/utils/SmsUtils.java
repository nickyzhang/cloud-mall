package com.cloud.common.core.utils;

import java.util.Random;

public class SmsUtils {

    private static Random random = new Random(System.currentTimeMillis());

    private static final String[] elements = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String genSmsCode(int len) {
        StringBuilder smsCodeBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(elements.length);
            smsCodeBuilder.append(elements[index]);
        }
        return smsCodeBuilder.toString();
    }
}
