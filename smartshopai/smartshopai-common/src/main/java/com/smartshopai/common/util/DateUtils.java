package com.smartshopai.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for date and time operations
 * Provides common date manipulation methods
 */
public final class DateUtils {
    
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    
    private DateUtils() {
        // Utility class
    }
    
    /**
     * Get current date time
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
    
    /**
     * Format date time to string
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }
    
    /**
     * Format date time to string with custom format
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }
    
    /**
     * Parse string to LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DEFAULT_FORMATTER);
    }
    
    /**
     * Parse string to LocalDateTime with custom format
     */
    public static LocalDateTime parse(String dateTimeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    
    /**
     * Convert Date to LocalDateTime
     */
    public static LocalDateTime fromDate(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
    }
    
    /**
     * Convert LocalDateTime to Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        return dateTime != null ? Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
    
    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }
}
