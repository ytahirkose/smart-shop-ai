package com.smartshopai.user.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.request.LoginRequest;
import com.smartshopai.user.application.dto.response.LoginResponse;
import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register a new user account")
    public ResponseEntity<BaseResponse<UserResponse>> registerUser(
            @Valid @RequestBody CreateUserRequest request) {
        
        log.info("Received user registration request - username: {}", request.getUsername());
        
        UserResponse response = userApplicationService.createUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(response, "User registered successfully"));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<BaseResponse<LoginResponse>> loginUser(
            @Valid @RequestBody LoginRequest request) {
        
        log.info("Received login request - emailOrUsername: {}", request.getEmailOrUsername());
        
        LoginResponse response = userApplicationService.loginUser(request);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Login successful"));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "User logout", description = "Logout user and invalidate token")
    public ResponseEntity<BaseResponse<Void>> logoutUser() {
        
        log.info("Received logout request");
        
        userApplicationService.logoutUser();
        
        return ResponseEntity.ok(BaseResponse.success(null, "Logout successful"));
    }

    @PostMapping("/refresh")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Refresh token", description = "Refresh JWT token")
    public ResponseEntity<BaseResponse<LoginResponse>> refreshToken(
            @RequestHeader("Authorization") String refreshToken) {
        
        log.info("Received token refresh request");
        
        LoginResponse response = userApplicationService.refreshToken(refreshToken);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Token refreshed successfully"));
    }

    @GetMapping("/validate")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Validate token", description = "Validate JWT token")
    public ResponseEntity<BaseResponse<Boolean>> validateToken(
            @RequestHeader("Authorization") String token) {
        
        log.debug("Received token validation request");
        
        boolean isValid = userApplicationService.validateToken(token);
        
        return ResponseEntity.ok(BaseResponse.success(isValid, "Token validation completed"));
    }
}
