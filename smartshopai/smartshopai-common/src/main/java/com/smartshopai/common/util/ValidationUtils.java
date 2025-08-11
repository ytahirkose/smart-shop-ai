package com.smartshopai.common.util;

import java.util.regex.Pattern;

public final class ValidationUtils {
    
    private ValidationUtils() {}
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );
    
    // Phone validation pattern (international format)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+?[1-9]\\d{1,14}$"
    );
    
    // Username validation pattern (alphanumeric, underscore, hyphen, 3-50 chars)
    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_-]{3,50}$"
    );
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) return false;
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }
    
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        // At least one uppercase, one lowercase, one digit
        return password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*\\d.*");
    }
    
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) return false;
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }
}
