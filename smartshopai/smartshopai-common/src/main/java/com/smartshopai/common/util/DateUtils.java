package com.smartshopai.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateUtils {
    
    private DateUtils() {}
    
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DEFAULT_FORMATTER);
    }
    
    public static String formatISODateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(ISO_FORMATTER);
    }
    
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateTimeString, DEFAULT_FORMATTER);
    }
    
    public static boolean isExpired(LocalDateTime dateTime) {
        if (dateTime == null) return true;
        return dateTime.isBefore(LocalDateTime.now());
    }
    
    public static long getDaysDifference(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.DAYS.between(start, end);
    }
    
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
