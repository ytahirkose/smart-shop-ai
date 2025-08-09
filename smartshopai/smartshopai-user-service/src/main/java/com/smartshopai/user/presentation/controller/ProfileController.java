package com.smartshopai.user.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.user.application.dto.request.UpdateProfileRequest;
import com.smartshopai.user.application.dto.request.UpdatePreferencesRequest;
import com.smartshopai.user.application.dto.response.UserProfileResponse;
import com.smartshopai.user.application.service.UserApplicationService;
import com.smartshopai.user.application.service.UserQueryService;
import com.smartshopai.user.application.service.UserProfileAppService;
import com.smartshopai.user.application.mapper.UserPreferencesMapper;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserPreferences;
import com.smartshopai.user.domain.repository.UserRepository;
import com.smartshopai.user.domain.service.UserPreferencesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Tag(name = "User Profiles", description = "User profile management endpoints")
public class ProfileController {

    private final UserApplicationService userApplicationService;
    private final UserQueryService userQueryService;
    private final UserProfileAppService userProfileService;
    private final UserRepository userRepository; // Inject UserRepository
    private final UserPreferencesMapper preferencesMapper; // Inject Mapper

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get user profile", description = "Get user profile by user ID")
    public ResponseEntity<BaseResponse<UserProfileResponse>> getUserProfile(
            @PathVariable String userId) {
        
        log.info("Getting user profile for userId: {}", userId);
        
        var profile = userQueryService.getUserProfile(userId);
        
        if (profile.isPresent()) {
            return ResponseEntity.ok(BaseResponse.success(profile.get(), "Profile retrieved successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    public ResponseEntity<BaseResponse<UserProfileResponse>> updateUserProfile(
            @PathVariable String userId,
            @Valid @RequestBody UpdateProfileRequest request) {
        
        log.info("Updating user profile for userId: {}", userId);
        
        UserProfileResponse response = userApplicationService.updateUserProfile(userId, request);
        
        return ResponseEntity.ok(BaseResponse.success(response, "Profile updated successfully"));
    }

    @GetMapping("/public")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get public profiles", description = "Get all public user profiles")
    public ResponseEntity<BaseResponse<List<UserProfileResponse>>> getPublicProfiles() {
        
        log.info("Getting public user profiles");
        
        List<UserProfileResponse> profiles = userQueryService.getPublicProfiles();
        
        return ResponseEntity.ok(BaseResponse.success(profiles, "Public profiles retrieved successfully"));
    }

    @GetMapping("/recommendations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get profiles for recommendations", description = "Get profiles that allow recommendations")
    public ResponseEntity<BaseResponse<List<UserProfileResponse>>> getProfilesForRecommendations() {
        
        log.info("Getting profiles for recommendations");
        
        List<UserProfileResponse> profiles = userQueryService.getProfilesForRecommendations();
        
        return ResponseEntity.ok(BaseResponse.success(profiles, "Profiles for recommendations retrieved successfully"));
    }

    @GetMapping("/data-analysis")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ANALYST')")
    @Operation(summary = "Get profiles for data analysis", description = "Get profiles that allow data analysis")
    public ResponseEntity<BaseResponse<List<UserProfileResponse>>> getProfilesForDataAnalysis() {
        
        log.info("Getting profiles for data analysis");
        
        List<UserProfileResponse> profiles = userQueryService.getProfilesForDataAnalysis();
        
        return ResponseEntity.ok(BaseResponse.success(profiles, "Profiles for data analysis retrieved successfully"));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user profile", description = "Delete user profile")
    public ResponseEntity<BaseResponse<Void>> deleteUserProfile(@PathVariable String userId) {
        
        log.info("Deleting user profile for userId: {}", userId);
        
        userApplicationService.deleteUserProfile(userId);
        
        return ResponseEntity.ok(BaseResponse.success(null, "Profile deleted successfully"));
    }

    @GetMapping("/me/preferences")
    public ResponseEntity<UserPreferences> getMyPreferences(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        UserPreferences preferences = userPreferencesService.getPreferencesByUserId(user.getId());
        return ResponseEntity.ok(preferences);
    }

    @PutMapping("/me/preferences")
    public ResponseEntity<UserPreferences> updateMyPreferences(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdatePreferencesRequest preferencesRequest) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        UserPreferences preferencesUpdate = preferencesMapper.toEntity(preferencesRequest);
        UserPreferences updatedPreferences = userPreferencesService.updatePreferences(user.getId(), preferencesUpdate);
        return ResponseEntity.ok(updatedPreferences);
    }
}
