package com.smartshopai.user.application.mapper;

import com.smartshopai.user.application.dto.request.CreateUserRequest;
import com.smartshopai.user.application.dto.request.UpdateProfileRequest;
import com.smartshopai.user.application.dto.response.UserResponse;
import com.smartshopai.user.application.dto.response.UserBehaviorMetricsResponse;
import com.smartshopai.user.application.dto.response.UserProfileResponse;
import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserBehaviorMetrics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for User entity and DTOs
 * Uses MapStruct for automatic mapping generation
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    
    /**
     * Map CreateUserRequest to User entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Will be encoded separately
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    User toEntity(CreateUserRequest request);

    /* UserProfile mappings */
    com.smartshopai.user.domain.entity.UserProfile toProfileEntity(CreateUserRequest request);
    com.smartshopai.user.domain.entity.UserProfile toProfileEntity(UpdateProfileRequest request);
    UserProfileResponse toProfileResponse(com.smartshopai.user.domain.entity.UserProfile profile);
    
    /**
     * Map User entity to UserResponse
     */
    UserResponse toResponse(User user);
    
    /**
     * Map UserBehaviorMetrics entity to UserBehaviorMetricsResponse DTO
     */
    UserBehaviorMetricsResponse toBehaviorMetricsResponse(UserBehaviorMetrics metrics);

    /**
     * Map list of User entities to list of UserResponse
     */
    List<UserResponse> toResponseList(List<User> users);
    

    
    /**
     * Custom mapping for behavior metrics
     */
    @Named("mapBehaviorMetrics")
    default String mapBehaviorMetrics(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getAiInsights() : null;
    }
    
    @Named("mapShoppingPersonality")
    default String mapShoppingPersonality(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getShoppingPersonality() : null;
    }
    
    @Named("mapTotalSearches")
    default Integer mapTotalSearches(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getTotalSearches() : 0;
    }
    
    @Named("mapTotalProductViews")
    default Integer mapTotalProductViews(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getTotalProductViews() : 0;
    }
    
    @Named("mapTotalPurchases")
    default Integer mapTotalPurchases(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getTotalPurchases() : 0;
    }
    
    @Named("mapTotalSpent")
    default Double mapTotalSpent(UserBehaviorMetrics metrics) {
        return metrics != null ? metrics.getTotalSpent() : 0.0;
    }
}
