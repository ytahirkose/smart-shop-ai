package com.smartshopai.monitoring.application.mapper;

import com.smartshopai.monitoring.application.dto.response.MetricResponse;
import com.smartshopai.monitoring.domain.entity.Metric;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for Monitoring entities and DTOs
 */
@Mapper(componentModel = "spring")
public interface MonitoringMapper {

    MetricResponse toResponse(Metric metric);

    List<MetricResponse> toResponseList(List<Metric> metrics);
}
