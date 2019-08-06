package com.cloud.common.core.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author nickyzhang
 */
public class DateUtils {

    public static LocalDateTime pasreToDateTime(String dateTime, String pattern) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime, formatter);
    }

    public static LocalDate pasreToDate(String date, String pattern) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String formatDate(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

    public static Long seconds(LocalDateTime dateTime) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now (ZoneId.systemDefault () );
        ZoneOffset defaultZoneOffset = offsetDateTime.getOffset ();
        return dateTime.toEpochSecond(defaultZoneOffset);
    }

    public static Long milliSeconds(LocalDateTime dateTime) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now (ZoneId.systemDefault () );
        ZoneOffset defaultZoneOffset = offsetDateTime.getOffset ();
        return dateTime.toInstant(defaultZoneOffset).toEpochMilli();
    }

    public static Long diffMilliSeconds(LocalDateTime src, LocalDateTime dest) {

        return milliSeconds(src) - milliSeconds(dest);
    }

    public static Long diffSeconds(LocalDateTime src, LocalDateTime dest) {

        return seconds(src) - seconds(dest);
    }
}
