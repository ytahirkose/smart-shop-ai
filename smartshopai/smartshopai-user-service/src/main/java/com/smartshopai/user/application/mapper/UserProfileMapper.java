package com.smartshopai.user.application.mapper;

import com.smartshopai.user.application.dto.request.UpdateProfileRequest;
import com.smartshopai.user.application.dto.response.UserProfileResponse;
import com.smartshopai.user.domain.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for UserProfile entity and DTOs
 * Uses MapStruct for automatic mapping generation
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    
    /**
     * Map UpdateProfileRequest to UserProfile entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserProfile toEntity(UpdateProfileRequest request);
    
    /**
     * Map UserProfile entity to UserProfileResponse
     */
    UserProfileResponse toResponse(UserProfile userProfile);
    
    /**
     * Map list of UserProfile entities to list of UserProfileResponse
     */
    List<UserProfileResponse> toResponseList(List<UserProfile> userProfiles);
}
