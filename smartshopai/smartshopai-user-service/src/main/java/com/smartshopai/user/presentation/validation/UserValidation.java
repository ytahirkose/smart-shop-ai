package com.smartshopai.user.presentation.validation;

import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.request.UpdateProfileRequest;
import com.smartshopai.user.domain.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validation component for User operations
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserValidationService userValidationService;

    /**
     * Validate user creation request
     */
    public void validateCreateUserRequest(CreateUserRequest request) {
        log.debug("Validating create user request for email: {}", request.getEmail());
        
        // Email validation
        if (!userValidationService.validateEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Username validation
        if (!userValidationService.validateUsername(request.getUsername())) {
            throw new IllegalArgumentException("Invalid username format");
        }
        
        // Password validation
        if (!userValidationService.validatePassword(request.getPassword())) {
            throw new IllegalArgumentException("Invalid password format");
        }
        
        // Check email uniqueness
        if (!userValidationService.isEmailUnique(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Check username uniqueness
        if (!userValidationService.isUsernameUnique(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
    }

    /**
     * Validate profile update request
     */
    public void validateUpdateProfileRequest(UpdateProfileRequest request) {
        log.debug("Validating update profile request");
        
        // Email validation if provided
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (!userValidationService.validateEmail(request.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
        
        // Phone validation if provided
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().trim().isEmpty()) {
            if (!userValidationService.validatePhoneNumber(request.getPhoneNumber())) {
                throw new IllegalArgumentException("Invalid phone number format");
            }
        }
    }
}
