package com.smartshopai.businessintelligence.application.mapper;

import com.smartshopai.businessintelligence.application.dto.request.TrackEventRequest;
import com.smartshopai.businessintelligence.application.dto.response.AnalyticsEventResponse;
import com.smartshopai.businessintelligence.application.dto.response.BusinessMetricResponse;
import com.smartshopai.businessintelligence.domain.entity.AnalyticsEvent;
import com.smartshopai.businessintelligence.domain.entity.BusinessMetric;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper for analytics entities and DTOs
 */
@Mapper(componentModel = "spring")
public interface AnalyticsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "processingError", ignore = true)
    AnalyticsEvent toEntity(TrackEventRequest request);

    AnalyticsEventResponse toResponse(AnalyticsEvent entity);

    List<AnalyticsEventResponse> toEventResponseList(List<AnalyticsEvent> entities);

    BusinessMetricResponse toResponse(BusinessMetric entity);

    List<BusinessMetricResponse> toMetricResponseList(List<BusinessMetric> entities);
}
