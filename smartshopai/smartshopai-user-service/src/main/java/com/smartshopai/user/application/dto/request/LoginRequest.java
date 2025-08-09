package com.smartshopai.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "Email or username is required")
    private String emailOrUsername;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    private boolean rememberMe;
}
