package com.cloud.common.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenUtils {

    private static List<String> generateBankCard(int count) {
        long l = 100000000000000000L;
        List<String> list = new ArrayList<String>();
        for (int a = 1; a <= count; a++) {
            String s = String.valueOf(l + a);
            char[] chs = s.toCharArray();
            int luhmSum = 0;
            for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
                int k = chs[i] - '0';
                if (j % 2 == 0) {
                    k *= 2;
                    k = k / 10 + k % 10;
                }
                luhmSum += k;
            }
            char b = (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
            String bankcard = s + b;
            if (isBankCard(bankcard)) {
                list.add(s + b);
            }
        }
        return list;
    }


    public static boolean isBankCard(String bankCard) {
        if (!StringUtils.isBlank(bankCard)) {
            String nonCheckCodeCardId = bankCard.substring(0, bankCard.length() - 1);
            if (nonCheckCodeCardId.matches("\\d+")) {
                char[] chs = nonCheckCodeCardId.toCharArray();
                int luhmSum = 0;
                for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
                    int k = chs[i] - '0';
                    if (j % 2 == 0) {
                        k *= 2;
                        k = k / 10 + k % 10;
                    }
                    luhmSum += k;
                }
                char b = (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
                return bankCard.charAt(bankCard.length() - 1) == b;
            }
        }
        return false;
    }


    public static String UUID32() {

        return UUID.randomUUID().toString().replace("-","").substring(0,32);
    }
}
