package com.cloud.common.core.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CacheTimeUtils {

    public static long generateCacheRandomTime(long origin, long bound) {
        if (origin < 0 || bound < 0) {
            throw new IllegalArgumentException("origin必须大于0，或者bound必须大于0");
        }
        if (origin >= bound) {
            throw new IllegalArgumentException("bound必须大于origin");
        }
        return ThreadLocalRandom.current().nextLong(origin,bound);
    }
}
