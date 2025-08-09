package com.smartshopai.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for login response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private LocalDateTime issuedAt;
    private UserResponse user;
    
    public static LoginResponse of(String token, String refreshToken, UserResponse user) {
        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L) // 24 hours
                .issuedAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
