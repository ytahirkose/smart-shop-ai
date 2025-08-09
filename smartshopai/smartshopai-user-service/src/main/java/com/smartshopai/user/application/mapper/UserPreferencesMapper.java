package com.smartshopai.user.application.mapper;

import com.smartshopai.user.application.dto.request.UpdatePreferencesRequest;
import com.smartshopai.user.domain.entity.UserPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserPreferencesMapper {
    UserPreferences toEntity(UpdatePreferencesRequest dto);
}
