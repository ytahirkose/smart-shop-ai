package com.smartshopai.user.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.mapper.UserMapper;
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
import java.util.List;
import java.util.Map;

/**
 * REST controller for User operations
 * Provides endpoints for user management
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management operations")
public class UserController {
    
    private final UserApplicationService userApplicationService;
    private final UserMapper userMapper;
    
    /**
     * Create new user
     */
    @PostMapping
    @Operation(summary = "Create new user", description = "Creates a new user account")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating new user with email: {}", request.getEmail());
        
        UserResponse userResponse = userApplicationService.createUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(userResponse, "User created successfully"));
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieves user information by ID")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable String id) {
        log.debug("Getting user by ID: {}", id);
        
        UserResponse userResponse = userApplicationService.getUserById(id);
        
        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }
    
    /**
     * Get current user profile
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get current user profile", description = "Retrieves current user's profile")
    public ResponseEntity<BaseResponse<UserResponse>> getCurrentUserProfile() {
        log.debug("Getting current user profile");
        
        UserResponse userResponse = userApplicationService.getCurrentUserProfile();
        
        return ResponseEntity.ok(BaseResponse.success(userResponse));
    }
    
    /**
     * Update user profile
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user profile", description = "Updates user profile information")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@PathVariable String id, 
                                                               @Valid @RequestBody CreateUserRequest request) {
        log.info("Updating user with ID: {}", id);
        
        UserResponse userResponse = userApplicationService.updateUser(id, request);
        
        return ResponseEntity.ok(BaseResponse.success(userResponse, "User updated successfully"));
    }
    
    @GetMapping("/{id}/insights")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get AI-generated insights for a user", description = "Retrieves marketing and behavioral insights for a specific user.")
    public ResponseEntity<BaseResponse<String>> getUserInsights(@PathVariable String id) {
        log.debug("Getting AI insights for user: {}", id);
        String insights = userApplicationService.getUserInsights(id);
        return ResponseEntity.ok(BaseResponse.success(insights, "Insights generated successfully"));
    }
    
    @PostMapping("/{id}/track-view")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Track a product view for a user", description = "Records that a user has viewed a specific product.")
    public ResponseEntity<Void> trackProductView(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String productId = payload.get("productId");
        userApplicationService.trackProductView(id, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/track-search")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Track a search query for a user", description = "Records a search query made by a user.")
    public ResponseEntity<Void> trackSearch(@PathVariable String id, @RequestBody Map<String, String> payload) {
        String searchQuery = payload.get("searchQuery");
        userApplicationService.trackSearch(id, searchQuery);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Update user behavior
     */
    @PostMapping("/{id}/behavior")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user behavior", description = "Updates user behavior metrics")
    public ResponseEntity<BaseResponse<Void>> updateUserBehavior(@PathVariable String id,
                                                               @RequestParam String action,
                                                               @RequestParam String data) {
        log.debug("Updating behavior for user: {}, action: {}", id, action);
        
        userApplicationService.updateUserBehavior(id, action, data);
        
        return ResponseEntity.ok(BaseResponse.success(null, "Behavior updated successfully"));
    }
    
    /**
     * Get users by preferences (for AI recommendations)
     */
    @GetMapping("/by-preferences")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get users by preferences", description = "Retrieves users by preferences for AI recommendations")
    public ResponseEntity<BaseResponse<List<UserResponse>>> getUsersByPreferences(
            @RequestParam List<String> categories,
            @RequestParam(required = false) Double maxBudget) {
        log.debug("Getting users by preferences - categories: {}, maxBudget: {}", categories, maxBudget);
        
        List<UserResponse> users = userApplicationService.getUsersByPreferences(categories, maxBudget);
        
        return ResponseEntity.ok(BaseResponse.success(users));
    }
    
    /**
     * Enable/disable user
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user status", description = "Enables or disables user account")
    public ResponseEntity<BaseResponse<Void>> updateUserStatus(@PathVariable String id,
                                                             @RequestParam boolean enabled) {
        log.info("Updating user status - ID: {}, enabled: {}", id, enabled);
        
        userApplicationService.setUserEnabled(id, enabled);
        
        return ResponseEntity.ok(BaseResponse.success(null, "User status updated successfully"));
    }
    
    /**
     * Verify user email
     */
    @PostMapping("/{id}/verify-email")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Verify user email", description = "Verifies user email address")
    public ResponseEntity<BaseResponse<Void>> verifyEmail(@PathVariable String id) {
        log.info("Verifying email for user: {}", id);
        
        userApplicationService.verifyEmail(id);
        
        return ResponseEntity.ok(BaseResponse.success(null, "Email verified successfully"));
    }
    
    /**
     * Verify user phone
     */
    @PostMapping("/{id}/verify-phone")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Verify user phone", description = "Verifies user phone number")
    public ResponseEntity<BaseResponse<Void>> verifyPhone(@PathVariable String id) {
        log.info("Verifying phone for user: {}", id);
        
        userApplicationService.verifyPhone(id);
        
        return ResponseEntity.ok(BaseResponse.success(null, "Phone verified successfully"));
    }
}
