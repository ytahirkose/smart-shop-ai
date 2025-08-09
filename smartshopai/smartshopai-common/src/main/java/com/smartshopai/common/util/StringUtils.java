package com.smartshopai.common.util;

import java.util.UUID;

/**
 * Utility class for string operations
 * Provides common string manipulation methods
 */
public final class StringUtils {
    
    private StringUtils() {
        // Utility class
    }
    
    /**
     * Check if string is null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /**
     * Generate random UUID
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Generate random UUID without hyphens
     */
    public static String generateUuidWithoutHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * Capitalize first letter
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    /**
     * Convert to camel case
     */
    public static String toCamelCase(String str) {
        if (isEmpty(str)) return str;
        
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        
        for (char c : str.toCharArray()) {
            if (c == '_' || c == '-') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        
        return result.toString();
    }
    
    /**
     * Convert to snake case
     */
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) return str;
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) result.append('_');
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    /**
     * Truncate string to specified length
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength) return str;
        return str.substring(0, maxLength) + "...";
    }
    
    /**
     * Remove special characters and keep only alphanumeric
     */
    public static String removeSpecialCharacters(String str) {
        if (isEmpty(str)) return str;
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }
}
