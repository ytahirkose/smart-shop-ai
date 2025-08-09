package com.smartshopai.sessioncache.application.mapper;

import com.smartshopai.sessioncache.application.dto.request.CreateSessionRequest;
import com.smartshopai.sessioncache.application.dto.request.UpdateSessionRequest;
import com.smartshopai.sessioncache.application.dto.response.UserSessionResponse;
import com.smartshopai.sessioncache.domain.entity.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for session entities and DTOs
 */
@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "lastActivityTime", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "durationSeconds", ignore = true)
    @Mapping(target = "pageViews", constant = "0")
    @Mapping(target = "clicks", constant = "0")
    @Mapping(target = "searches", constant = "0")
    @Mapping(target = "visitedPages", ignore = true)
    @Mapping(target = "searchedTerms", ignore = true)
    @Mapping(target = "viewedProducts", ignore = true)
    @Mapping(target = "currentPage", ignore = true)
    @Mapping(target = "sessionData", ignore = true)
    @Mapping(target = "cookies", ignore = true)
    @Mapping(target = "aiContext", ignore = true)
    @Mapping(target = "recommendations", ignore = true)
    @Mapping(target = "userPreferences", ignore = true)
    @Mapping(target = "authenticationToken", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "tokenExpiryTime", ignore = true)
    @Mapping(target = "isAuthenticated", constant = "false")
    @Mapping(target = "analyticsData", ignore = true)
    @Mapping(target = "metrics", ignore = true)
    UserSession toEntity(CreateSessionRequest request);

    UserSessionResponse toResponse(UserSession entity);

    List<UserSessionResponse> toResponseList(List<UserSession> entities);
}
