package com.smartshopai.user.domain.service;

import com.smartshopai.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Domain service for user data validation
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    
    /**
     * Validate user data
     */
    public void validateUserData(String email, String username, String password, String phoneNumber) {
        log.debug("Validating user data for email: {}, username: {}", email, username);
        
        validateEmail(email);
        validateUsername(username);
        validatePassword(password);
        validatePhoneNumber(phoneNumber);
    }
    
    /**
     * Validate email format
     */
    public void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        if (email.length() > 255) {
            throw new ValidationException("Email is too long (max 255 characters)");
        }
    }
    
    /**
     * Validate username format
     */
    public void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be null or empty");
        }
        
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new ValidationException("Username must be 3-20 characters long and contain only letters, numbers, and underscores");
        }
    }
    
    /**
     * Validate password strength
     */
    public void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be null or empty");
        }
        
        if (password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }
        
        if (password.length() > 128) {
            throw new ValidationException("Password is too long (max 128 characters)");
        }
        
        // Check for at least one uppercase letter, one lowercase letter, and one digit
        if (!password.matches(".*[A-Z].*")) {
            throw new ValidationException("Password must contain at least one uppercase letter");
        }
        
        if (!password.matches(".*[a-z].*")) {
            throw new ValidationException("Password must contain at least one lowercase letter");
        }
        
        if (!password.matches(".*\\d.*")) {
            throw new ValidationException("Password must contain at least one digit");
        }
    }
    
    /**
     * Validate phone number format
     */
    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be null or empty");
        }
        
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ValidationException("Invalid phone number format");
        }
    }
    
    /**
     * Validate user preferences
     */
    public void validateUserPreferences(Double maxBudget, String preferredBrands, String shoppingPreferences) {
        if (maxBudget != null && maxBudget <= 0) {
            throw new ValidationException("Max budget must be positive");
        }
        
        if (maxBudget != null && maxBudget > 1000000) {
            throw new ValidationException("Max budget cannot exceed 1,000,000");
        }
        
        if (preferredBrands != null && preferredBrands.length() > 1000) {
            throw new ValidationException("Preferred brands list is too long");
        }
        
        if (shoppingPreferences != null && shoppingPreferences.length() > 1000) {
            throw new ValidationException("Shopping preferences are too long");
        }
    }
    
    /**
     * Validate user profile data
     */
    public void validateUserProfileData(String firstName, String lastName) {
        if (firstName != null && (firstName.trim().isEmpty() || firstName.length() > 50)) {
            throw new ValidationException("First name must be between 1 and 50 characters");
        }
        
        if (lastName != null && (lastName.trim().isEmpty() || lastName.length() > 50)) {
            throw new ValidationException("Last name must be between 1 and 50 characters");
        }
    }
    
    /**
     * Validate user roles
     */
    public void validateUserRoles(java.util.Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new ValidationException("User must have at least one role");
        }
        
        for (String role : roles) {
            if (role == null || role.trim().isEmpty()) {
                throw new ValidationException("Role cannot be null or empty");
            }
            
            if (!role.matches("^[A-Z_]+$")) {
                throw new ValidationException("Role must contain only uppercase letters and underscores");
            }
        }
    }
}
